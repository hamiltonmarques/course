package com.ead.course.controllers;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.ResponseDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.CourseUserService;
import com.ead.course.specifications.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private CourseUserService courseUserService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(UserFilter userFilter,
                                      @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        PageResponse<UserDTO> pageResponse = courseUserService.getUsers(userFilter, pageable);
        return ResponseDTO.ok("Users listed successfully", pageResponse);
    }

    @PostMapping("/user/{userId}/subscribe/{courseId}")
    public ResponseEntity<?> subscribeUserInCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
        log.info("subscribing user in course...");
        courseUserService.subscribeUserInCourse(userId, courseId);
        return ResponseDTO.created("User subscribed in course successfully", userId);
    }
}
