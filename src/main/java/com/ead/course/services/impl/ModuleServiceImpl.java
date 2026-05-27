package com.ead.course.services.impl;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.ModuleCreateDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.dtos.ModuleUpdateDTO;
import com.ead.course.dtos.PageDTO;
import com.ead.course.exception.notfound.CourseNotFoundException;
import com.ead.course.exception.notfound.ModuleNotFoundException;
import com.ead.course.mappers.ModuleMapper;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleMapper moduleMapper;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public ModuleDTO createModule(UUID courseId, ModuleCreateDTO moduleCreateDTO) {
        CourseModel courseModel = findCourse(courseId);

        ModuleModel moduleModel = moduleMapper.toModel(moduleCreateDTO);
        moduleModel.setCourse(courseModel);
        moduleRepository.save(moduleModel);

        return moduleMapper.toDTO(moduleRepository.save(moduleModel));
    }

    @Override
    public void deleteModule(UUID moduleId, UUID courseId) {
        ModuleModel moduleModel = findModule(moduleId, courseId);
        moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleDTO updateModule(UUID moduleId, UUID courseId, ModuleUpdateDTO moduleUpdateDTO) {
        ModuleModel moduleModel = findModule(moduleId, courseId);

        moduleMapper.updateModel(moduleUpdateDTO, moduleModel);
        moduleRepository.save(moduleModel);

        return moduleMapper.toDTO(moduleModel);
    }

    @Override
    public PageResponse<ModuleDTO> findAll(UUID courseId, Pageable pageable) {
        Page<ModuleDTO> moduleDTOPage = moduleRepository.findByCourseId(courseId, pageable)
                .map(moduleMapper::toDTO);

        return new PageResponse<>(
                moduleDTOPage.getContent(),
                new PageDTO(
                        moduleDTOPage.getNumber(),
                        moduleDTOPage.getSize(),
                        moduleDTOPage.getTotalElements(),
                        moduleDTOPage.getTotalPages(),
                        moduleDTOPage.hasNext(),
                        moduleDTOPage.hasPrevious()
                )
        );
    }

    @Override
    public ModuleDTO getModule(UUID moduleId, UUID courseId) {
        ModuleModel moduleModel = findModule(moduleId, courseId);
        return moduleMapper.toDTO(moduleModel);
    }

    private ModuleModel findModule(UUID moduleId, UUID courseId) {
        return moduleRepository.findByIdAndCourseId(moduleId, courseId)
                .orElseThrow(ModuleNotFoundException::new);
    }

    private CourseModel findCourse(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);
    }
}
