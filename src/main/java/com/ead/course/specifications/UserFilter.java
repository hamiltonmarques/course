package com.ead.course.specifications;

import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserFilter {
    private UserType type;
    private UserStatus status;
    private String email;
    private UUID courseId;
}