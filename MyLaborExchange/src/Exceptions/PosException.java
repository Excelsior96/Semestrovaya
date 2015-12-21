package Exceptions;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class PosException extends Exception {

    public PosException() {
        super();
    }

    public PosException(String message) {
        super(message);
    }

    public PosException(String message, Throwable cause) {
        super(message, cause);
    }

    public PosException(Throwable cause) {
        super(cause);
    }

}
