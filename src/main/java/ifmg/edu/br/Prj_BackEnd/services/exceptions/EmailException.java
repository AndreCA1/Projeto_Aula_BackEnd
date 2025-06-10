package ifmg.edu.br.Prj_BackEnd.services.exceptions;

import org.springframework.mail.MailException;

public class EmailException extends RuntimeException {
    public EmailException(String message){
        super(message);
    }

}
