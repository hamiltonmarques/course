package com.ead.course.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LessonDTO {
    private UUID id;
    private String title;
    private String description;
    private String videoUrl;
    private LocalDateTime createdAt;
}