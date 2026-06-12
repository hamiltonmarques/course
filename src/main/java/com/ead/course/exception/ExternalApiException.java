package com.ead.course.exception;

import com.ead.course.api.response.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalApiException extends RuntimeException {

    private final String originApi;
    private final HttpStatus httpStatus;
    private final ApiResponse<?> apiResponse;

    public ExternalApiException(String originApi, HttpStatus httpStatus, ApiResponse<?> apiResponse) {
        super(apiResponse.getMessage());
        this.originApi = originApi;
        this.httpStatus = httpStatus;
        this.apiResponse = apiResponse;
    }
}
