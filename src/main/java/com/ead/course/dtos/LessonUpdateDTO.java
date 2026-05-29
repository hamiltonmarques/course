package com.ead.course.dtos;

import lombok.Data;

@Data
public class LessonUpdateDTO {
    private String title;
    private String description;
    private String videoUrl;
}