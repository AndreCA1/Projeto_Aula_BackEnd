package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
//Passo a classe e a chave primaria do que quero criar o meu CRUD
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
