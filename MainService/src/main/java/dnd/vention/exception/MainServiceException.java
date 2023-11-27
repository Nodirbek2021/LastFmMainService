package dnd.vention.exception;

public class MainServiceException extends RuntimeException{
    public MainServiceException() {
        super();
    }

    public MainServiceException(String message) {
        super(message);
    }

    public MainServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MainServiceException(Throwable cause) {
        super(cause);
    }


}
