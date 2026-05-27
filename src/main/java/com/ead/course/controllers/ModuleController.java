package com.ead.course.controllers;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.ModuleCreateDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.dtos.ModuleUpdateDTO;
import com.ead.course.dtos.ResponseDTO;
import com.ead.course.services.ModuleService;
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
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> getModules(@PathVariable(value = "courseId") UUID courseId,
                                        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        PageResponse<ModuleDTO> pageResponse = moduleService.findAll(courseId, pageable);
        return ResponseDTO.ok("Modules listed successfully", pageResponse);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> getModule(@PathVariable(value = "courseId") UUID courseId,
                                       @PathVariable(value = "moduleId") UUID moduleId) {
        ModuleDTO moduleDTO = moduleService.getModule(moduleId, courseId);
        return ResponseDTO.ok("Module found successfully", moduleDTO);
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> createModule(@PathVariable(value = "courseId") UUID courseId,
                                          @RequestBody @Validated ModuleCreateDTO moduleCreateDTO) {
        ModuleDTO moduleDTO = moduleService.createModule(courseId, moduleCreateDTO);
        return ResponseDTO.created("Module created successfully", moduleDTO);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                                          @PathVariable(value = "moduleId") UUID moduleId) {
        moduleService.deleteModule(moduleId, courseId);
        return ResponseDTO.ok("Module deleted successfully", moduleId);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                          @PathVariable(value = "moduleId") UUID moduleId,
                                          @RequestBody @Validated ModuleUpdateDTO moduleUpdateDTO) {
        ModuleDTO moduleDTO = moduleService.updateModule(moduleId, courseId, moduleUpdateDTO);
        return ResponseDTO.ok("Module updated successfully", moduleDTO);
    }
}
