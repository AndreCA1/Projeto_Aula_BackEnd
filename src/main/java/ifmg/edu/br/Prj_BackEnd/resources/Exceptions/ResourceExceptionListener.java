package ifmg.edu.br.Prj_BackEnd.resources.Exceptions;

import ifmg.edu.br.Prj_BackEnd.services.exceptions.DataBaseException;
import ifmg.edu.br.Prj_BackEnd.services.exceptions.ResourceNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

//Essa classe é responsável por tratar todos os seus erros, basta add os metódos para cada erro escutado

//Indica para o Spring boot que essa classe vai ficar escutando excessões
@ControllerAdvice
public class ResourceExceptionListener {

    //Indica que quando o erro "ResourceNotFound.class" essa é a função que deve ser chamada
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StandartError> resourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
        StandartError error = new StandartError();

        error.setTimestamp(Instant.now());
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setError("Resource not found");
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandartError> databaseException(DataBaseException ex, HttpServletRequest request) {
        StandartError error = new StandartError();

        error.setTimestamp(Instant.now());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setError("Database exception");
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
