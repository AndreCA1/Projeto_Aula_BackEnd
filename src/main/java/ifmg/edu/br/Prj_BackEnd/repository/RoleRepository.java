package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //JPA ja cria o sql pra mim 
    public Role findByAuthority(String roleOperator);
}
