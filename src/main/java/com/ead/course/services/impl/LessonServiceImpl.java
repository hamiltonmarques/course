package com.ead.course.services.impl;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.LessonCreateDTO;
import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.LessonUpdateDTO;
import com.ead.course.dtos.PageDTO;
import com.ead.course.exception.notfound.LessonNotFoundException;
import com.ead.course.exception.notfound.ModuleNotFoundException;
import com.ead.course.mappers.LessonMapper;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public LessonDTO createLesson(UUID moduleId, LessonCreateDTO lessonCreateDTO) {
        ModuleModel moduleModel = findModule(moduleId);

        LessonModel lessonModel = lessonMapper.toModel(lessonCreateDTO);
        lessonModel.setModule(moduleModel);
        lessonRepository.save(lessonModel);

        return lessonMapper.toDTO(lessonModel);
    }

    @Override
    public void deleteLesson(UUID lessonId, UUID moduleId) {
        LessonModel lessonModel = findLesson(lessonId, moduleId);
        lessonRepository.delete(lessonModel);
    }

    @Override
    public LessonDTO updateLesson(UUID lessonId, UUID moduleId, LessonUpdateDTO lessonUpdateDTO) {
        LessonModel lessonModel = findLesson(lessonId, moduleId);

        lessonMapper.updateModel(lessonUpdateDTO, lessonModel);
        lessonRepository.save(lessonModel);

        return lessonMapper.toDTO(lessonModel);
    }

    @Override
    public PageResponse<LessonDTO> findAll(UUID moduleId, Pageable pageable) {
        Page<LessonDTO> lessonDTOPage = lessonRepository.findByModuleId(moduleId, pageable)
                .map(lessonMapper::toDTO);

        return new PageResponse<>(
                lessonDTOPage.getContent(),
                new PageDTO(
                        lessonDTOPage.getNumber(),
                        lessonDTOPage.getSize(),
                        lessonDTOPage.getTotalElements(),
                        lessonDTOPage.getTotalPages(),
                        lessonDTOPage.hasNext(),
                        lessonDTOPage.hasPrevious()
                )
        );
    }

    @Override
    public LessonDTO getLesson(UUID lessonId, UUID moduleId) {
        LessonModel lessonModel = findLesson(lessonId, moduleId);
        return lessonMapper.toDTO(lessonModel);
    }

    private LessonModel findLesson(UUID lessonId, UUID moduleId) {
        return lessonRepository.findByIdAndModuleId(lessonId, moduleId)
                .orElseThrow(LessonNotFoundException::new);
    }

    private ModuleModel findModule(UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(ModuleNotFoundException::new);
    }
}
