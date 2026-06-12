package com.ead.course.services.impl;

import com.ead.course.api.response.PageResponse;
import com.ead.course.clients.AuthUserApiClient;
import com.ead.course.dtos.UserDTO;
import com.ead.course.enums.CourseStatus;
import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;
import com.ead.course.exception.business.InvalidCourseException;
import com.ead.course.exception.business.InvalidUserException;
import com.ead.course.exception.notfound.CourseNotFoundException;
import com.ead.course.exception.validation.UserAlreadySubscribedException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import com.ead.course.specifications.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private AuthUserApiClient authUserApiClient;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Override
    public PageResponse<UserDTO> getUsers(UserFilter userFilter, Pageable pageable) {
        return authUserApiClient.getUsers(userFilter, pageable);
    }

    @Override
    public void subscribeUserInCourse(UUID userId, UUID courseId) {
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        log.info("course found successfully");

        if (course.getStatus() == CourseStatus.FINISHED) {
            throw new InvalidCourseException("Only in progress courses can receive users");
        }

        validateUser(userId);

        if (courseUserRepository.existsByUserIdAndCourse(userId, course)) {
            throw new UserAlreadySubscribedException();
        }

        log.info("user can be subscribed");

        authUserApiClient.subscribeUserInCourse(userId, courseId);

        CourseUserModel courseUser = new CourseUserModel();
        courseUser.setUserId(userId);
        courseUser.setCourse(course);

        courseUserRepository.save(courseUser);

        log.info("user subscribed in course successfully");
    }

    private void validateUser(UUID userId) {
        UserDTO user = authUserApiClient.getUser(userId);

        if (user.getType() != UserType.STUDENT) {
            throw new InvalidUserException("Only students users can be subscribed");
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new InvalidUserException("Only active users can be subscribed");
        }

        log.info("user validated successfully");
    }
}
