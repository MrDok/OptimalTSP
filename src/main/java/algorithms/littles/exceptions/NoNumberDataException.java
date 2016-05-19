package algorithms.littles.exceptions;

/**
 * Created by Alexander on 17.05.2016.
 */
public class NoNumberDataException extends Exception {

    public NoNumberDataException() {
    }

    public NoNumberDataException(String message) {
        super(message);
    }

    public NoNumberDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNumberDataException(Throwable cause) {
        super(cause);
    }
}
