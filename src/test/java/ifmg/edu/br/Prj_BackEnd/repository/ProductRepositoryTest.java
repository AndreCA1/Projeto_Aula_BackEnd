package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

//Não carrega a aplicacao inteiro, apenas a parte de repository
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName(value = "Verificando se o objeto não existe no BD depois de deletado")
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(1L);
        Optional<Product> obj = productRepository.findById(1L);

        Assertions.assertFalse(obj.isPresent());
    }

}
