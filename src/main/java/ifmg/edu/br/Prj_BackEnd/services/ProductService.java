package ifmg.edu.br.Prj_BackEnd.services;

import ifmg.edu.br.Prj_BackEnd.dtos.CategoryDTO;
import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.entities.Product;
import ifmg.edu.br.Prj_BackEnd.repository.CategoryRepository;
import ifmg.edu.br.Prj_BackEnd.repository.ProductRepository;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.DataBaseException;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        //Metodo lambda
        //return page.map(product -> new ProductDTO(product));

        //Referencia de metodo
        return page.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product product = obj.orElseThrow( () -> new ResourceNotFound("Product not found: " + id));

        //Converte o produto e suas categorias em DTO
        return new ProductDTO(product);
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
