package com.thomsonreuters.grc.fsp.ums.client.core.exception;

/**
 * Exception to indicate an error occur during client creation , group creation
 */
public class CreateClientException extends RuntimeException {

    /**
     * Generated SerialVersionUID
     */
    private static final long serialVersionUID = 4234483281459592581L;

    /**
     * Create a new default CreateClientException with no detail message or cause.
     */
    public CreateClientException() {
    }

    /**
     * Create a new CreateClientException with given reason on why the CreateClient
     * operation failed.
     *
     * @param message The reason (message) for the exception.
     */
    public CreateClientException(String message) {
        super(message);
    }

    /**
     * Create a new CreateClientException with given message and exception cause.
     *
     * @param message The detail message
     * @param cause   The cause of the exception
     */
    public CreateClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new CreateClientException for the given exception cause.
     *
     * @param cause The cause of the exception
     */
    public CreateClientException(Throwable cause) {
        super(cause);
    }
}
