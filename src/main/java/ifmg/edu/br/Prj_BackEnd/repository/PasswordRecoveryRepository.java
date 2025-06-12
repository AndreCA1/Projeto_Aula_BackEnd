package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecover, Long> {

    @Query("SELECT objeto FROM PasswordRecover objeto WHERE (objeto.token = :token) AND (objeto.expiration > :now)")
    public PasswordRecover searchValidToken(String token, Instant now);
}
