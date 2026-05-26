package com.ead.course.services;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.CourseCreateDTO;
import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.CourseUpdateDTO;
import com.ead.course.specifications.CourseFilter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {
    CourseDTO createCourse(CourseCreateDTO courseCreateDTO);

    void deleteCourse(UUID id);

    CourseDTO updateCourse(UUID id, CourseUpdateDTO courseUpdateDTO);

    PageResponse<CourseDTO> findAll(CourseFilter courseFilter, Pageable pageable);

    CourseDTO getUser(UUID id);
}
