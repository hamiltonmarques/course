package com.ead.course.exception.notfound;

public class ModuleNotFoundException extends NotFoundException {
    public ModuleNotFoundException() {
        super("Module not found");
    }
}
