package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.User;
import ifmg.edu.br.Prj_BackEnd.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true,
           value = """
                    SELECT u.email as UserEmail,
                           u.password,
                           r.id as RoleId,
                           r.authority
                    FROM tb_user u 
                        INNER JOIN userRole ur 
                                ON u.id = ur.user_id 
                                    INNER JOIN role r 
                                        ON r.id = ur.role_id
                    WHERE u.email = :username
                   """
    )
    List<UserDetailsProjection> searchUserByAndRoleByEmail(String username);
}
