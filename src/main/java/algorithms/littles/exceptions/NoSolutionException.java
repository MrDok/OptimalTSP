package algorithms.littles.exceptions;

/**
 * Created by Alexander on 17.05.2016.
 */
public class NoSolutionException extends Exception {
    public NoSolutionException() {
    }

    public NoSolutionException(String message) {
        super(message);
    }

    public NoSolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSolutionException(Throwable cause) {
        super(cause);
    }
}
