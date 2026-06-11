package com.ead.course.specifications;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class CourseSpecification {

    public static Specification<CourseModel> byFilter(CourseFilter filter) {
        Specification<CourseModel> spec = Specification.where(null);

        if (filter.getLevel() != null) {
            spec = spec.and(levelEquals(filter.getLevel()));
        }

        if (filter.getStatus() != null) {
            spec = spec.and(statusEquals(filter.getStatus()));
        }

        if (filter.getInstructorId() != null) {
            spec = spec.and(instructorIdEquals(filter.getInstructorId()));
        }

        if (filter.getName() != null && !filter.getName().isBlank()) {
            spec = spec.and(nameLike(filter.getName()));
        }

        if (filter.getUserId() != null) {
            spec = spec.and(hasUserId(filter.getUserId()));
        }

        return spec;
    }

    private static Specification<CourseModel> levelEquals(CourseLevel level) {
        return (root, query, cb) ->
                cb.equal(root.get("level"), level);
    }

    private static Specification<CourseModel> statusEquals(CourseStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    private static Specification<CourseModel> instructorIdEquals(UUID id) {
        return (root, query, cb) ->
                cb.equal(root.get("instructorId"), id);
    }

    private static Specification<CourseModel> nameLike(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<CourseModel> hasUserId(UUID id) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> join = root.join("courseUsers");
            return cb.equal(join.get("userId"), id);
        };
    }
}