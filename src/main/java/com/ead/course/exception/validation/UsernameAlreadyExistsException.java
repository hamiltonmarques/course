package com.ead.course.exception.validation;

public class UsernameAlreadyExistsException extends AlreadyExistsException {
    public UsernameAlreadyExistsException() {
        super("Username already exists");
    }
}
