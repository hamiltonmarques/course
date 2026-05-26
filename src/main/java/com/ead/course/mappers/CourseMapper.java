package com.ead.course.mappers;

import com.ead.course.dtos.CourseCreateDTO;
import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.CourseUpdateDTO;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.CourseModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", imports = {CourseStatus.class, CourseLevel.class})
public interface CourseMapper {

    CourseDTO toDTO(CourseModel courseModel);

    @Mapping(target = "status", expression = "java(CourseStatus.IN_PROGRESS)")
    @Mapping(target = "level", expression = "java(CourseLevel.BEGINNER)")
    CourseModel toModel(CourseCreateDTO courseCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModel(CourseUpdateDTO courseUpdateDTO, @MappingTarget CourseModel courseModel);
}
