package domain.model.errores;

public class DataModificationException extends RuntimeException {

    public DataModificationException(String message) {
        super(message);
    }
}
