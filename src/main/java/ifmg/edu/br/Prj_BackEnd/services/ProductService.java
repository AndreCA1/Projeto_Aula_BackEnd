package ifmg.edu.br.Prj_BackEnd.services;

import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.entities.Product;
import ifmg.edu.br.Prj_BackEnd.repository.CategoryRepository;
import ifmg.edu.br.Prj_BackEnd.repository.ProductRepository;
import ifmg.edu.br.Prj_BackEnd.resources.ProductResource;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.DataBaseException;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Static serve para n ter q Escrever a classe toda ('WebMvcLinkBuilder.linkTo'), apenas chamar a classe ('linkTo')
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);

        //Metodo lambda, transforma cada product em productDTO
        return page.map(product -> new ProductDTO(product)
        //Criando a propriedade link:
                            //nessa classe (product resourse) eu acesso o metodo findAll
                        .add(linkTo(methodOn(ProductResource.class).findAll(null)).withSelfRel())
                        .add(linkTo(methodOn(ProductResource.class).findById(product.getId())).withRel("Get a product"))
        );

        //Referencia de metodo, transforma cada product em productDTO
        //return page.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product product = obj.orElseThrow( () -> new ResourceNotFound("Product not found: " + id));

        //Converte o produto e suas categorias em DTO
        return new ProductDTO(product)
//                .add(linkTo(methodOn(ProductResource.class).findById(product.getId())).withSelfRel())
//                .add(linkTo(methodOn(ProductResource.class).findAll()).withRel("All Products"))
//                .add(linkTo(methodOn(ProductResource.class).update()).withRel("Update Product"))
//                .add(linkTo(methodOn(ProductResource.class).delete(product.getId())).withRel("Delete Product"))
                ;
    }

    @Transactional
    //Recebe o DTO
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();

        //Passa pra entity
        copyDtoToEntity(dto, entity);

        //Salva no BD e pega o id
        entity = productRepository.save(entity);
        //Retorna em DTO
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try{
            Product entity = productRepository.getReferenceById(id);
            //joga o q está no dto para o entity
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Product not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFound("Product not found: " + id);
        }
        try{
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImageURL(dto.getImageURL());
        dto.getCategories().forEach(c -> entity.getCategories().add(new Category(c)));
    }
}
