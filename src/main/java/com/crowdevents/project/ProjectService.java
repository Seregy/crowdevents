package com.crowdevents.project;

import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProjectService {
    Project create(String name, String description, Money fundingGoal, Person owner);
    Project get(UUID id);
    Iterable<Project> getAll();
    void delete(UUID id);
    void changeName(UUID id, String newName);
    void changeDescription(UUID id, String newDescription);
    void changeFundingGoal(UUID id, Money newGoal);
    void changeLocation(UUID id, Location newLocation);
    void changeStartDateTime(UUID id, LocalDateTime newStartDateTime);
    void changeEndDateTime(UUID id, LocalDateTime newEndDateTime);
    void addVideoLink(UUID id, String... links);
    void addImageLink(UUID id, String... links);
    void addOwner(UUID id, Person... owners);
}
