package org.appsec.securityrat.api;

/**
 * An exception that indicates that a required
 * {@link org.appsec.securityrat.api.dto.Account} is not activated.
 */
public class NotActivatedException extends Exception {
    public NotActivatedException() {
    }
    
    public NotActivatedException(String message) {
        super(message);
    }
    
    public NotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotActivatedException(Throwable cause) {
        super(cause);
    }
}
