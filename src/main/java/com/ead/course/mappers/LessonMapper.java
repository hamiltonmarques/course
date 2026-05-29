package com.ead.course.mappers;

import com.ead.course.dtos.LessonCreateDTO;
import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.LessonUpdateDTO;
import com.ead.course.models.LessonModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonDTO toDTO(LessonModel lessonModel);

    LessonModel toModel(LessonCreateDTO lessonCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModel(LessonUpdateDTO lessonUpdateDTO, @MappingTarget LessonModel lessonModel);
}
