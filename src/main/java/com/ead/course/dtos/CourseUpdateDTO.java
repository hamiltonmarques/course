package com.ead.course.dtos;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseUpdateDTO {
    private UUID instructorId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus status;
    private CourseLevel level;
}