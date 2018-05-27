package org.drombler.jstore.client.agent.startup.integration;

public class JStoreClientException extends Exception {
    private static final long serialVersionUID = -1;

    private final Integer statusCode;

    public JStoreClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public JStoreClientException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = null;
    }

    public JStoreClientException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }


    public Integer getStatusCode() {
        return statusCode;
    }
}
