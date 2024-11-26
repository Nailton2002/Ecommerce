package com.api.restfull.ecommerce.domain.exception;

public class BusinessRuleExceptionLogger extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BusinessRuleExceptionLogger(String msg) {
        super(msg);
    }
}
