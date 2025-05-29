package ifmg.edu.br.Prj_BackEnd.resources;

import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.services.ProductService;
import ifmg.edu.br.Prj_BackEnd.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ProductResourceTest {

    //Responsavel pelas requisições
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private long existingId;
    private long nonExistingId;
    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 200L;
        productDTO = Factory.createProductDTO();
        productDTO.setId(existingId);
        page = new PageImpl<>(List.of(productDTO));
    }

    @Test
    @DisplayName("Verificando ")
    void findAllShouldReturnAllPages() throws Exception {
        when(productService.findAll(any())).thenReturn(page);

        //Testa requisição
        ResultActions result = mockMvc.perform(get("/product").accept("application/json"));

        //Analisa resultado
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(existingId));
        result.andExpect(jsonPath("$.content[0].name").value(productDTO.getName()));
        result.andExpect(jsonPath("$.content[0].description").value(productDTO.getDescription()));


    }
}