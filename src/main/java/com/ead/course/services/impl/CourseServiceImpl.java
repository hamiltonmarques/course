package com.ead.course.services.impl;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.CourseCreateDTO;
import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.CourseUpdateDTO;
import com.ead.course.dtos.PageDTO;
import com.ead.course.exception.notfound.CourseNotFoundException;
import com.ead.course.mappers.CourseMapper;
import com.ead.course.models.CourseModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.CourseFilter;
import com.ead.course.specifications.CourseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public CourseDTO createCourse(CourseCreateDTO courseCreateDTO) {
        CourseModel courseModel = courseMapper.toModel(courseCreateDTO);
        courseRepository.save(courseModel);
        return courseMapper.toDTO(courseModel);
    }

    @Override
    public void deleteCourse(UUID id) {
        CourseModel courseModel = findCourse(id);
        // TODO test if CASCADE works later!
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseDTO updateCourse(UUID id, CourseUpdateDTO courseUpdateDTO) {
        CourseModel courseModel = findCourse(id);

        courseMapper.updateModel(courseUpdateDTO, courseModel);
        courseRepository.save(courseModel);

        return courseMapper.toDTO(courseModel);
    }

    @Override
    public PageResponse<CourseDTO> findAll(CourseFilter courseFilter, Pageable pageable) {
        Specification<CourseModel> spec = CourseSpecification.byFilter(courseFilter);
        Page<CourseDTO> courseDTOPage = courseRepository.findAll(spec, pageable)
                .map(courseMapper::toDTO);

        return new PageResponse<>(
                courseDTOPage.getContent(),
                new PageDTO(
                        courseDTOPage.getNumber(),
                        courseDTOPage.getSize(),
                        courseDTOPage.getTotalElements(),
                        courseDTOPage.getTotalPages(),
                        courseDTOPage.hasNext(),
                        courseDTOPage.hasPrevious()
                )
        );
    }

    @Override
    public CourseDTO getUser(UUID id) {
        CourseModel courseModel = findCourse(id);
        return courseMapper.toDTO(courseModel);
    }

    private CourseModel findCourse(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);
    }
}
