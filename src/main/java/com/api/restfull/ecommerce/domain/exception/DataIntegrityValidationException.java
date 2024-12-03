package com.api.restfull.ecommerce.domain.exception;

import org.springframework.dao.NonTransientDataAccessException;

public class DataIntegrityViolationException extends NonTransientDataAccessException {

    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationException(String msg) {
        super(msg);

    }
}
