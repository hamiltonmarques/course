package com.ead.course.controllers;

import com.ead.course.dtos.ResponseDTO;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request, HttpServletResponse response) {
        int statusCode = response.getStatus();

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            WebRequest webRequest = new ServletWebRequest(request);
            Map<String, Object> options = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
            String originalUri = (String) options.getOrDefault("path", "requested URL");

            if (originalUri.contains("//")) {
                return ResponseDTO.badRequest(String.format("%s is not valid", originalUri));
            }

            return ResponseDTO.notFound(String.format("%s not found", originalUri));
        }

        return ResponseDTO.internalError("Unexpected internal error");
    }
}