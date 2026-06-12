package com.ead.course.services;

import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.UserDTO;
import com.ead.course.specifications.UserFilter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {

    PageResponse<UserDTO> getUsers(UserFilter userFilter, Pageable pageable);

    void subscribeUserInCourse(UUID userId, UUID courseId);
}
