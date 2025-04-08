package ifmg.edu.br.Prj_BackEnd.services.exceptions;

public class DataBaseException extends RuntimeException {
    public DataBaseException() {
        super();
    }
    public DataBaseException(String message) {
        super(message);
    }
}
