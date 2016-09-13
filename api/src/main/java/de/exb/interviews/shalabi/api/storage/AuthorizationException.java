package de.exb.interviews.shalabi.api.storage;

/**
 * Authorization exception.
 */
public class AuthorizationException extends Exception {
    public AuthorizationException(final String aMessage) {
        super(aMessage);
    }

    public AuthorizationException(final String aMessage, final Throwable aThrowable) {
        super(aMessage, aThrowable);
    }

}
