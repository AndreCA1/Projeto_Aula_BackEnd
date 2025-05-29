package ifmg.edu.br.Prj_BackEnd.resources;

import ifmg.edu.br.Prj_BackEnd.dtos.CategoryDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.repository.CategoryRepository;
import ifmg.edu.br.Prj_BackEnd.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //Cria um parametro(pode ou não ser recebido) o nome da variavel e do value precisa ser o mesmo
    //Esses parametros servem para paginação(quantas objetos por pag, se vai ser ascendente ou decrescente e pelo que vai ordenar(id, nome,etc...))
    public ResponseEntity<Page<CategoryDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                     @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                     @RequestParam(value = "orderby", defaultValue = "id") String orderby){

        //Crio um objeto pageable com os parametros recebidos e passo para o fideAll
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderby);
        Page<CategoryDTO> categories = categoryService.findAll(pageable);

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

    //Define a resposta para um metodo post
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PostMapping
    //ResquestBody (Receberei esse parametro no corpo da mensagem)
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = categoryService.insert(dto);

        //Pegar o caminho da minha aplicação da requisição atual e adiciona uma nova parte com o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    //Define a resposta para um metodo put(editar) + caminho
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<CategoryDTO> update(@PathVariable long id, @RequestBody CategoryDTO dto){
        dto = categoryService.update(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    //Define a resposta para um metodo delet(deletar) + caminho
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<Void> delete(@PathVariable long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
