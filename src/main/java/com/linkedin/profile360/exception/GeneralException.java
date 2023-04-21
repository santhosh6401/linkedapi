package com.linkedin.profile360.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralException extends ResponseStatusException {
    public GeneralException(HttpStatus httpStatus, String errorMsg) {
        super(httpStatus, errorMsg);
    }
}
