package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
