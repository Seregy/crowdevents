package com.crowdevents.project;

import com.crowdevents.location.Location;

import java.time.LocalDateTime;
import java.util.Optional;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Project create(String name, String shortDescription, Money fundingGoal, Long ownerId);

    Optional<Project> get(Long id);

    Page<Project> getAll(Pageable pageable);

    Page<Project> getAll(ProjectType type, ProjectVisibility visibility, Pageable pageable);

    Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, Pageable pageable);

    Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, ProjectType type,
                                         ProjectVisibility visibility, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Project updatedProject);
}
