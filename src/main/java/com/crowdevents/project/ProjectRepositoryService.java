package com.crowdevents.project;

import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

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
    public Project create(String name, String description, Money fundingGoal, Long... ownersIds) {
        Person[] persons = Arrays.stream(ownersIds).map(this::getPerson).toArray(Person[]::new);
        Project project = new Project(name, description, fundingGoal, persons);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> get(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Page<Project> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, Pageable pageable) {
        if (beforeId != null && afterId != null) {
            return projectRepository.findAllByIdAfterAndIdBefore(afterId, beforeId, pageable);
        } else if (beforeId != null){
            return projectRepository.findAllByIdBefore(beforeId, pageable);
        } else if (afterId != null) {
            return projectRepository.findAllByIdAfter(afterId, pageable);
        } else {
            throw new IllegalArgumentException("Either beforeId or afterId must not be null");
        }
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void changeName(Long id, String newName) {
        Project project = getProject(id);
        project.setName(newName);
        projectRepository.save(project);
    }

    @Override
    public void changeDescription(Long id, String newDescription) {
        Project project = getProject(id);
        project.setDescription(newDescription);
        projectRepository.save(project);
    }

    @Override
    public void changeFundingGoal(Long id, Money newGoal) {
        Project project = getProject(id);
        project.setFundingGoal(newGoal);
        projectRepository.save(project);
    }

    @Override
    public void changeLocation(Long id, Location newLocation) {
        Project project = getProject(id);
        project.setLocation(newLocation);
        projectRepository.save(project);
    }

    @Override
    public void changeStartDateTime(Long id, LocalDateTime newStartDateTime) {
        Project project = getProject(id);
        project.setStartDateTime(newStartDateTime);
        projectRepository.save(project);
    }

    @Override
    public void changeEndDateTime(Long id, LocalDateTime newEndDateTime) {
        Project project = getProject(id);
        project.setEndDateTime(newEndDateTime);
        projectRepository.save(project);
    }

    @Override
    public void addVideoLink(Long id, String... links) {
        Project project = getProject(id);
        project.getVideoLinks().addAll(Arrays.asList(links));
        projectRepository.save(project);
    }

    @Override
    public void addImageLink(Long id, String... links) {
        Project project = getProject(id);
        project.getImageLinks().addAll(Arrays.asList(links));
        projectRepository.save(project);
    }

    @Override
    public void addOwner(Long id, Long... ownersIds) {
        Project project = getProject(id);
        for (Long ownerId : ownersIds) {
            Person owner = getPerson(ownerId);
            project.addOwner(owner);
        }
        projectRepository.save(project);
    }

    @Override
    public void update(Long id, Project newProject) {
        Project project = getProject(id);
        project.setName(newProject.getName());
        project.setDescription(newProject.getDescription());
        project.setLocation(newProject.getLocation());
        project.setStartDateTime(newProject.getStartDateTime());
        project.setEndDateTime(newProject.getEndDateTime());
        project.setFundingGoal(newProject.getFundingGoal());
        projectRepository.save(project);
    }

    private Person getPerson(Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }

    private Project getProject(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }
}
