package ifmg.edu.br.Prj_BackEnd.resources;

import ifmg.edu.br.Prj_BackEnd.dtos.CategoryDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.repository.CategoryRepository;
import ifmg.edu.br.Prj_BackEnd.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//Define essa classe como quem responde a requisições
@RestController
//Passa o caminho "url"
@RequestMapping(value = "/category")

public class CategoryResource {
    @Autowired
    private CategoryService categoryService;


    //Define a resposta para um metodo get
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){

        List <CategoryDTO> categories = categoryService.findAll();

        // o .ok retorna o código 200, .create seria 201, etc
        // .body é o retorno para o usuário
        return ResponseEntity.ok().body(categories);
    }

    //Passa o que vai diferenciar os gets, nesse caso ele receberá o id
    @GetMapping(value = "/{id}")
    //@PathVariable avisa pro java q é uma variable
    public ResponseEntity<CategoryDTO> findByID(@PathVariable long id){
        CategoryDTO category= categoryService.findById(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = categoryService.insert(dto);

        //Pegar o caminho da minha aplicação da requisição atual e adiciona uma nova parte com o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable long id, @RequestBody CategoryDTO dto){
        dto = categoryService.update(id, dto);

        return ResponseEntity.ok().body(dto);
    }
}
