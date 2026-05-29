package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {
    Optional<LessonModel> findByIdAndModuleId(UUID lessonId, UUID moduleId);

    Page<LessonModel> findByModuleId(UUID moduleId, Pageable pageable);
}
