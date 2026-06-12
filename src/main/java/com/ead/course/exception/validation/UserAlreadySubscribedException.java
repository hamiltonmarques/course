package com.ead.course.exception.validation;

public class UserAlreadySubscribedException extends AlreadyExistsException {
    public UserAlreadySubscribedException() {
        super("User already subscribed");
    }
}
