package com.ead.course.services;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.LessonCreateDTO;
import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.LessonUpdateDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LessonService {
    LessonDTO createLesson(UUID moduleId, LessonCreateDTO lessonCreateDTO);

    void deleteLesson(UUID lessonId, UUID moduleId);

    LessonDTO updateLesson(UUID lessonId, UUID moduleId, LessonUpdateDTO lessonUpdateDTO);

    PageResponse<LessonDTO> findAll(UUID moduleId, Pageable pageable);

    LessonDTO getLesson(UUID lessonId, UUID moduleId);
}
