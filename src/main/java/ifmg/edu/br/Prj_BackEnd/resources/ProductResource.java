package ifmg.edu.br.Prj_BackEnd.resources;

import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.dtos.ProductListDTO;
import ifmg.edu.br.Prj_BackEnd.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/product")
//Descrição dele no swagger
@Tag(name = "Product", description = "Controller/Resource for product")
public class ProductResource {


    @Autowired
    private ProductService productService;

    //Muda o tipo de retorno e altera no swagger
    @GetMapping(produces = "application/json")
    @Operation(
            description = "Find all",
            summary = "Find all",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403")
            })
    //Pageable tem todas as propridades de paginação
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> products = productService.findAll(pageable);
        //Nova sintaxe
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/paged", produces = "application/json")
    @Operation(
            description = "Find all Paged",
            summary = "Find all Paged",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403")
            })
    //Pageable tem todas as propridades de paginação
    public ResponseEntity<Page<ProductListDTO>> findAllPaged(Pageable pageable,
        @RequestParam(value = "CategoryId", defaultValue = "0") String categoryId,
        @RequestParam(value = "name", defaultValue = "")String name) {

            Page<ProductListDTO> products = productService.findAllPaged(name, categoryId, pageable);
            //Nova sintaxe
            return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Find product by ID",
            summary = "Find product by ID",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "NotFound", responseCode = "404")
            })
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO products = productService.findById(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping(produces = "application/json")
    @Operation(
            description = "Create a new Product",
            summary = "Create a new Product",
            responses = {
                    @ApiResponse(description = "created", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403")
        })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
        dto = productService.insert(dto);

        //Pegar o caminho da minha aplicação da requisição atual e adiciona uma nova parte com o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Update product",
            summary = "Update product",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "NotFound", responseCode = "404")
            })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<ProductDTO> update(@Valid @PathVariable Long id, @RequestBody ProductDTO dto) {
        dto = productService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            description = "Delete product",
            summary = "Delete product",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "NotFound", responseCode = "404")
            })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
