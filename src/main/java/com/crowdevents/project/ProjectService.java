package com.crowdevents.project;

import com.crowdevents.location.Location;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Project create(String name, String description, Money fundingGoal, UUID... ownersIds);

    Optional<Project> get(UUID id);

    Page<Project> getAll(Pageable pageable);

    Page<Project> getAllAfter(Pageable pageable, UUID afterId);

    void delete(UUID id);

    void changeName(UUID id, String newName);

    void changeDescription(UUID id, String newDescription);

    void changeFundingGoal(UUID id, Money newGoal);

    void changeLocation(UUID id, Location newLocation);

    void changeStartDateTime(UUID id, LocalDateTime newStartDateTime);

    void changeEndDateTime(UUID id, LocalDateTime newEndDateTime);

    void addVideoLink(UUID id, String... links);

    void addImageLink(UUID id, String... links);

    void addOwner(UUID id, UUID... ownersIds);
}
