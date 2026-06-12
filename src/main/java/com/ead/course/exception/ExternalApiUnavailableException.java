package com.ead.course.exception;

import lombok.Getter;

@Getter
public class ExternalApiUnavailableException extends RuntimeException {

    private final String originApi;

    public ExternalApiUnavailableException(String originApi, Throwable cause) {
        super(originApi + " is unavailable", cause);
        this.originApi = originApi;
    }
}
