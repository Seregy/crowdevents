package com.crowdevents.update;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UpdateRepositoryService implements UpdateService {
    private UpdateRepository updateRepository;
    private ProjectRepository projectRepository;
    private Clock clock;

    @Autowired
    public UpdateRepositoryService(UpdateRepository updateRepository,
                                   ProjectRepository projectRepository, Clock clock) {
        this.updateRepository = updateRepository;
        this.projectRepository = projectRepository;
        this.clock = clock;
    }

    @Override
    public Update post(UUID projectId, String message) {
        Project project = getProject(projectId);
        Update update = new Update(project, LocalDateTime.now(clock), message);
        return updateRepository.save(update);
    }

    @Override
    public Optional<Update> get(UUID id) {
        return updateRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        updateRepository.deleteById(id);
    }

    @Override
    public void changeMessage(UUID id, String newMessage) {
        Update update = getUpdate(id);
        update.setMessage(newMessage);
        updateRepository.save(update);
    }

    @Override
    public void changeShortMessage(UUID id, String newShortMessage) {
        Update update = getUpdate(id);
        update.setShortMessage(newShortMessage);
        updateRepository.save(update);
    }

    private Project getProject(UUID id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }

    private Update getUpdate(UUID id) {
        return updateRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid update id: " + id));
    }
}
