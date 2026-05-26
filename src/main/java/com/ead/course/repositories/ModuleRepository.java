package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    // use EntityGraph to return fields that are LAZY in database
    // it will change to EAGER in runtime and data is displayed
    @EntityGraph(attributePaths = {"course"})
    ModuleModel findByTitle(String title);
}
