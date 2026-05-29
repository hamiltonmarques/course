package com.ead.course.controllers;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.LessonCreateDTO;
import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.LessonUpdateDTO;
import com.ead.course.dtos.ResponseDTO;
import com.ead.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<?> getLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        PageResponse<LessonDTO> pageResponse = lessonService.findAll(moduleId, pageable);
        return ResponseDTO.ok("Lessons listed successfully", pageResponse);
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> getLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                       @PathVariable(value = "lessonId") UUID lessonId) {
        LessonDTO lessonDTO = lessonService.getLesson(lessonId, moduleId);
        return ResponseDTO.ok("Lesson found successfully", lessonDTO);
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<?> createLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                          @RequestBody @Validated LessonCreateDTO lessonCreateDTO) {
        LessonDTO lessonDTO = lessonService.createLesson(moduleId, lessonCreateDTO);
        return ResponseDTO.created("Lesson created successfully", lessonDTO);
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                          @PathVariable(value = "lessonId") UUID lessonId) {
        lessonService.deleteLesson(lessonId, moduleId);
        return ResponseDTO.ok("Lesson deleted successfully", lessonId);
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                          @PathVariable(value = "lessonId") UUID lessonId,
                                          @RequestBody @Validated LessonUpdateDTO lessonUpdateDTO) {
        LessonDTO lessonDTO = lessonService.updateLesson(lessonId, moduleId, lessonUpdateDTO);
        return ResponseDTO.ok("Lesson updated successfully", lessonDTO);
    }
}
