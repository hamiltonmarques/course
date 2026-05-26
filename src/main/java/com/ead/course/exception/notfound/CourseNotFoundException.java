package com.ead.course.exception.notfound;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException() {
        super("Course not found");
    }
}
