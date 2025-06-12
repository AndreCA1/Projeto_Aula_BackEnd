package ifmg.edu.br.Prj_BackEnd.services;

import ifmg.edu.br.Prj_BackEnd.dtos.EmailDTO;
import ifmg.edu.br.Prj_BackEnd.dtos.NewPasswordDTO;
import ifmg.edu.br.Prj_BackEnd.dtos.ResquestTokenDTO;
import ifmg.edu.br.Prj_BackEnd.entities.PasswordRecover;
import ifmg.edu.br.Prj_BackEnd.entities.User;
import ifmg.edu.br.Prj_BackEnd.repository.PasswordRecoveryRepository;
import ifmg.edu.br.Prj_BackEnd.repository.UserRepository;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private int tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String uri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private PasswordRecoveryRepository passwordRecoveryRepository;

    public void createRecoverToken(ResquestTokenDTO dto) {
        //Busca user pelo email
        User entity = userRepository.findByEmail(dto.getEmail());

        if (entity == null) throw new ResourceNotFound("Email not found!");
        //gera token
        String token = UUID.randomUUID().toString();

        //inserir no BD
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(entity.getEmail());
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

        passwordRecoveryRepository.save(passwordRecover);

        String body = "Acesse o link para definir uma nova senha: " +
                "\nVálido por " + tokenMinutes + " minutos" +
                "\n\n" + uri + token;
        EmailDTO email = new EmailDTO(entity.getEmail(), "Password Recover", body);
        emailService.sendMail(email);
    }

    public void saveNewPassword(NewPasswordDTO dto) {
        PasswordRecover recover = passwordRecoveryRepository.searchValidToken(dto.getToken(), Instant.now());

        if(recover == null){
            throw new ResourceNotFound("Token not found");
        }

        User entity = userRepository.findByEmail(recover.getEmail());

        entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}