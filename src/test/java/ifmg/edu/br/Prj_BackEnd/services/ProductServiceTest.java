package ifmg.edu.br.Prj_BackEnd.services;

import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Product;
import ifmg.edu.br.Prj_BackEnd.repository.ProductRepository;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import ifmg.edu.br.Prj_BackEnd.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
//Moka os dados(define os dados base para teste)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    //Java entende q tudo dentro de productService deve ser mocado
    @InjectMocks
    private ProductService productService;

    //Quem vai ser mocado
    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 200L;
        Product product = Factory.createProduct();
        page = new PageImpl<>(List.of(product, product));
    }

    @Test
    @DisplayName("Verificando se o objeto foi deletado no BD")
    void deleteShouldDoNothingWhenIdExists() {
        when(productRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(existingId);

        //Função anonima
        Assertions.assertDoesNotThrow(() -> productService.delete(existingId));
        verify(productRepository, times(1)).deleteById(existingId);
        productService.delete(existingId);
    }

    @Test
    @DisplayName("Verificando se levanta exceção se o objeto não existe no BD")
    void deleteShouldThrowExceptionWhenIdDoesNotExist() {
        when(productRepository.existsById(nonExistingId)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFound.class, () -> productService.delete(nonExistingId));
        verify(productRepository, times(0)).deleteById(nonExistingId);
    }

    @Test
    @DisplayName("Verificando se o findAll retorna os dados paginados")
    void findAllShouldReturnOnePage() {
        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Pageable pagina = PageRequest.of(0, 10);
        Page<ProductDTO> result = productService.findAll(pagina);

        Assertions.assertNotNull(result);
        verify(productRepository, times(1)).findAll(pagina);
    }
}