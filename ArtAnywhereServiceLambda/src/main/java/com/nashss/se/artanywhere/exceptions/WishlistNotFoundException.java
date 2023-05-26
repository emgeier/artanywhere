package com.nashss.se.artanywhere.exceptions;

public class WishlistNotFoundException extends RuntimeException {
    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public WishlistNotFoundException(String message) {
        super(message);
    }
    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public WishlistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Exception without message and cause.
     */
    public WishlistNotFoundException() {

    }
}
