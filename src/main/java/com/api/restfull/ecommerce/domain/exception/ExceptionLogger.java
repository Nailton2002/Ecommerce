package com.api.restfull.ecommerce.domain.exception;

public class ExceptionLogger extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExceptionLogger(String msg) {
        super(msg);
    }
}
