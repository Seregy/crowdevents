package com.crowdevents.project;

import com.crowdevents.location.Location;

import java.time.LocalDateTime;
import java.util.Optional;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Project create(String name, String description, Money fundingGoal, Long... ownersIds);

    Optional<Project> get(Long id);

    Page<Project> getAll(Pageable pageable);

    Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, Pageable pageable);

    void delete(Long id);

    void changeName(Long id, String newName);

    void changeDescription(Long id, String newDescription);

    void changeFundingGoal(Long id, Money newGoal);

    void changeLocation(Long id, Location newLocation);

    void changeStartDateTime(Long id, LocalDateTime newStartDateTime);

    void changeEndDateTime(Long id, LocalDateTime newEndDateTime);

    void addVideoLink(Long id, String... links);

    void addImageLink(Long id, String... links);

    void addOwner(Long id, Long... ownersIds);

    void update(Long id, Project newProject);
}
