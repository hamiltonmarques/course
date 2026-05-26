package com.ead.course.specifications;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseFilter {
    private String name;
    private UUID instructorId;
    private CourseStatus status;
    private CourseLevel level;
}