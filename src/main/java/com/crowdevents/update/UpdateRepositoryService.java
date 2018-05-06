package com.crowdevents.update;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateRepositoryService implements UpdateService {
    private UpdateRepository updateRepository;
    private ProjectRepository projectRepository;
    private Clock clock;

    /**
     * Creates new update service that uses repositories.
     *
     * @param updateRepository update repository
     * @param projectRepository project repository
     * @param clock clock for generating date and time
     */
    @Autowired
    public UpdateRepositoryService(UpdateRepository updateRepository,
                                   ProjectRepository projectRepository, Clock clock) {
        this.updateRepository = updateRepository;
        this.projectRepository = projectRepository;
        this.clock = clock;
    }

    @Override
    public Update post(Long projectId, String message) {
        Project project = getProject(projectId);
        Update update = new Update(project, LocalDateTime.now(clock), message);
        return updateRepository.save(update);
    }

    @Override
    public Optional<Update> get(Long id) {
        return updateRepository.findById(id);
    }

    @Override
    public Page<Update> getAll(Pageable pageable) {
        return updateRepository.findAll(pageable);
    }

    @Override
    public Page<Update> getAllByProject(Long projectId, Pageable pageable) {
        return updateRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (updateRepository.existsById(id)) {
            updateRepository.deleteById(id);
        }

        return !updateRepository.existsById(id);
    }

    @Override
    public void update(Long id, Update updatedUpdate) {
        if (updatedUpdate == null) {
            throw new IllegalArgumentException("Updated update must not be null");
        }

        Update update = getUpdate(id);
        update.setMessage(updatedUpdate.getMessage());
        update.setShortMessage(updatedUpdate.getShortMessage());
        updateRepository.save(update);
    }

    private Project getProject(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }

    private Update getUpdate(Long id) {
        return updateRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid update id: " + id));
    }
}
