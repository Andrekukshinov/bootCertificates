package com.epam.esm.web.exception;

public class InvalidSizeException extends RuntimeException{
    public InvalidSizeException() {
        super();
    }

    public InvalidSizeException(String message) {
        super(message);
    }

    public InvalidSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSizeException(Throwable cause) {
        super(cause);
    }
}
