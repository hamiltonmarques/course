package com.ead.course.exception.notfound;

public class LessonNotFoundException extends NotFoundException {
    public LessonNotFoundException() {
        super("Lesson not found");
    }
}
