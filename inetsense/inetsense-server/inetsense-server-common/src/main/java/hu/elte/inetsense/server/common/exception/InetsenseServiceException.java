package hu.elte.inetsense.server.common.exception;

/**
 * @author Zsolt Istvanfi
 */
public class InetsenseServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public InetsenseServiceException() {
        super();
    }

    public InetsenseServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InetsenseServiceException(final String message) {
        super(message);
    }

}
