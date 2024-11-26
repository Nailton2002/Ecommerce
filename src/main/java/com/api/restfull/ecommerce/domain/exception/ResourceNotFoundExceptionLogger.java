package com.api.restfull.ecommerce.domain.exception;

public class ResourceNotFoundExceptionLogger extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundExceptionLogger(String msg) {
        super(msg);
    }
}
