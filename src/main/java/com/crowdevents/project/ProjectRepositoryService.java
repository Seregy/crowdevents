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
    public Project create(String name, String shortDescription,
                          Money fundingGoal, Long ownerId) {
        Person person = getPerson(ownerId);
        Project project = new Project(name, shortDescription, fundingGoal, person);
        project.setType(ProjectType.IN_CREATION);
        project.setVisibility(ProjectVisibility.PRIVATE);
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
    public Page<Project> getAll(ProjectType type, ProjectVisibility visibility, Pageable pageable) {
        return projectRepository.findAllByTypeAndVisibility(type, visibility, pageable);
    }

    @Override
    public Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, Pageable pageable) {
        if (beforeId != null && afterId != null) {
            return projectRepository.findAllByIdAfterAndIdBefore(afterId, beforeId, pageable);
        } else if (beforeId != null) {
            return projectRepository.findAllByIdBefore(beforeId, pageable);
        } else if (afterId != null) {
            return projectRepository.findAllByIdAfter(afterId, pageable);
        } else {
            throw new IllegalArgumentException("Either beforeId or afterId must not be null");
        }
    }

    @Override
    public Page<Project> getAllBeforeAndOrAfter(Long beforeId, Long afterId, ProjectType type,
                                                ProjectVisibility visibility, Pageable pageable) {
        if (beforeId != null && afterId != null) {
            return projectRepository.findAllByIdAfterAndIdBeforeAndTypeAndVisibility(afterId,
                    beforeId, type, visibility, pageable);
        } else if (beforeId != null) {
            return projectRepository.findAllByIdBeforeAndTypeAndVisibility(beforeId, type,
                    visibility, pageable);
        } else if (afterId != null) {
            return projectRepository.findAllByIdAfterAndTypeAndVisibility(afterId, type,
                    visibility, pageable);
        } else {
            throw new IllegalArgumentException("Either beforeId or afterId must not be null");
        }
    }

    @Override
    public boolean delete(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }

        return !projectRepository.existsById(id);
    }

    @Override
    public void update(Long id, Project updatedProject) {
        if (updatedProject == null) {
            throw new IllegalArgumentException("Updated project must not be null");
        }

        Project project = getProject(id);
        project.setName(updatedProject.getName());
        project.setShortDescription(updatedProject.getShortDescription());
        project.setDescription(updatedProject.getDescription());
        project.setLocation(updatedProject.getLocation());
        project.setStartDateTime(updatedProject.getStartDateTime());
        project.setEndDateTime(updatedProject.getEndDateTime());
        project.setEventDateTime(updatedProject.getEventDateTime());
        project.setFundingGoal(updatedProject.getFundingGoal());
        project.setProjectImageLink(updatedProject.getProjectImageLink());
        project.setType(updatedProject.getType());
        project.setVisibility(updatedProject.getVisibility());
        project.setTeamMembers(updatedProject.getTeamMembers());
        project.setImageLinks(updatedProject.getImageLinks());
        project.setVideoLinks(updatedProject.getVideoLinks());
        project.setPaymentAccountId(updatedProject.getPaymentAccountId());
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
