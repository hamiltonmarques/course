package com.ead.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseCreateDTO {
    @NotNull
    private UUID instructorId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
}