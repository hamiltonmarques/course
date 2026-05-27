package com.ead.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ModuleDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}