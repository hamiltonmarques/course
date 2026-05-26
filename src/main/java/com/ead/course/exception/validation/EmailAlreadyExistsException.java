package com.ead.course.exception.validation;

public class EmailAlreadyExistsException extends AlreadyExistsException {
    public EmailAlreadyExistsException() {
        super("Email already exists");
    }
}
