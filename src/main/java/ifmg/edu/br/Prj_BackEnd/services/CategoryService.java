package ifmg.edu.br.Prj_BackEnd.services;

import ifmg.edu.br.Prj_BackEnd.dtos.CategoryDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.repository.CategoryRepository;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    //O spring vai gerenciar esse objeto, vai dar new e n preciso excluí-lo
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List <Category> list = categoryRepository.findAll();

        //Map acessa cada posição do vetor e chama o objeto de x, passa ele para o tipo DTO[" new CategoryDTO(x)) "] e cria uma nova lista do tipo DTO [" collect(Collectors.toList() "]
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category category = obj.orElseThrow( () -> new ResourceNotFound("Category not found: " + id));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());

        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(long id, CategoryDTO dto) {
        try {
            //Apenas referencia o objeto, mas não trás o objeto em sí já que vc vai alterá-lo
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Category not found: " + id);
        }
    }
}
