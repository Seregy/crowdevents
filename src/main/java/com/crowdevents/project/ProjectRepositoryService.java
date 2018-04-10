package com.crowdevents.project;

import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectRepositoryService implements ProjectService {
    private ProjectRepository projectRepository;
    private PersonRepository personRepository;

    public ProjectRepositoryService(ProjectRepository projectRepository,
                                    PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Project create(String name, String description, Money fundingGoal, UUID... ownersIds) {
        Person[] persons = Arrays.stream(ownersIds).map(this::getPerson).toArray(Person[]::new);
        Project project = new Project(name, description, fundingGoal, persons);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> get(UUID id) {
        return projectRepository.findById(id);
    }

    @Override
    public Page<Project> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> getAllAfter(Pageable pageable, UUID afterId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void changeName(UUID id, String newName) {
        Project project = getProject(id);
        project.setName(newName);
        projectRepository.save(project);
    }

    @Override
    public void changeDescription(UUID id, String newDescription) {
        Project project = getProject(id);
        project.setDescription(newDescription);
        projectRepository.save(project);
    }

    @Override
    public void changeFundingGoal(UUID id, Money newGoal) {
        Project project = getProject(id);
        project.setFundingGoal(newGoal);
        projectRepository.save(project);
    }

    @Override
    public void changeLocation(UUID id, Location newLocation) {
        Project project = getProject(id);
        project.setLocation(newLocation);
        projectRepository.save(project);
    }

    @Override
    public void changeStartDateTime(UUID id, LocalDateTime newStartDateTime) {
        Project project = getProject(id);
        project.setStartDateTime(newStartDateTime);
        projectRepository.save(project);
    }

    @Override
    public void changeEndDateTime(UUID id, LocalDateTime newEndDateTime) {
        Project project = getProject(id);
        project.setEndDateTime(newEndDateTime);
        projectRepository.save(project);
    }

    @Override
    public void addVideoLink(UUID id, String... links) {
        Project project = getProject(id);
        project.getVideoLinks().addAll(Arrays.asList(links));
        projectRepository.save(project);
    }

    @Override
    public void addImageLink(UUID id, String... links) {
        Project project = getProject(id);
        project.getImageLinks().addAll(Arrays.asList(links));
        projectRepository.save(project);
    }

    @Override
    public void addOwner(UUID id, UUID... ownersIds) {
        Project project = getProject(id);
        for (UUID ownerId : ownersIds) {
            Person owner = getPerson(ownerId);
            project.addOwner(owner);
        }
        projectRepository.save(project);
    }

    private Person getPerson(UUID id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }

    private Project getProject(UUID id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }
}
