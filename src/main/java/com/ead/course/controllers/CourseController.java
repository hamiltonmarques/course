package com.ead.course.controllers;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.CourseCreateDTO;
import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.CourseUpdateDTO;
import com.ead.course.dtos.ResponseDTO;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.CourseFilter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getCourses(CourseFilter courseFilter,
                                        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        PageResponse<CourseDTO> pageResponse = courseService.findAll(courseFilter, pageable);
        return ResponseDTO.ok("Courses listed successfully", pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable(value = "id") UUID id) {
        CourseDTO courseDTO = courseService.getUser(id);
        return ResponseDTO.ok("Course found successfully", courseDTO);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody @Validated CourseCreateDTO courseCreateDTO) {
        CourseDTO courseDTO = courseService.createCourse(courseCreateDTO);
        return ResponseDTO.created("Course created successfully", courseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseDTO.ok("Course deleted successfully", id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID id, @RequestBody @Validated CourseUpdateDTO courseUpdateDTO) {
        CourseDTO courseDTO = courseService.updateCourse(id, courseUpdateDTO);
        return ResponseDTO.ok("Course updated successfully", courseDTO);
    }
}
