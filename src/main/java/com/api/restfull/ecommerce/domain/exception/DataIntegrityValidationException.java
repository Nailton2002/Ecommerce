package com.api.restfull.ecommerce.domain.exception;

public class DataIntegrityValidationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DataIntegrityValidationException(String msg) {
        super(msg);

    }
}
