package com.ead.course.services;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.ModuleCreateDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.dtos.ModuleUpdateDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ModuleService {
    ModuleDTO createModule(UUID courseId, ModuleCreateDTO moduleCreateDTO);

    void deleteModule(UUID moduleId, UUID courseId);

    ModuleDTO updateModule(UUID moduleId, UUID courseId, ModuleUpdateDTO moduleUpdateDTO);

    PageResponse<ModuleDTO> findAll(UUID courseId, Pageable pageable);

    ModuleDTO getModule(UUID moduleId, UUID courseId);
}
