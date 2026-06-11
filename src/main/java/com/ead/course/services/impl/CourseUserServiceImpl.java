package com.ead.course.services.impl;

import com.ead.course.api.response.PageResponse;
import com.ead.course.clients.AuthUserApiClient;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.CourseUserService;
import com.ead.course.specifications.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private AuthUserApiClient authUserApiClient;

    @Override
    public PageResponse<UserDTO> getUsers(UserFilter userFilter, Pageable pageable) {
        return authUserApiClient.getUsers(userFilter, pageable);
    }
}
