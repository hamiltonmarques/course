package com.ead.course.mappers;

import com.ead.course.dtos.ModuleCreateDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.dtos.ModuleUpdateDTO;
import com.ead.course.models.ModuleModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    ModuleDTO toDTO(ModuleModel moduleModel);

    ModuleModel toModel(ModuleCreateDTO moduleCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModel(ModuleUpdateDTO moduleUpdateDTO, @MappingTarget ModuleModel courseModel);
}
