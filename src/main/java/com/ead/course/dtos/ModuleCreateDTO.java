package com.ead.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModuleCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}