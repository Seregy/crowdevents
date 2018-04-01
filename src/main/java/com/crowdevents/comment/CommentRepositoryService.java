package com.crowdevents.comment;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
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
public class CommentRepositoryService implements CommentService {
    private CommentRepository commentRepository;
    private ProjectRepository projectRepository;
    private PersonRepository personRepository;
    private Clock clock;

    @Autowired
    public CommentRepositoryService(CommentRepository commentRepository,
                                    ProjectRepository projectRepository,
                                    PersonRepository personRepository,
                                    Clock clock) {
        this.commentRepository = commentRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.clock = clock;
    }

    @Override
    public Comment post(UUID projectId, UUID personId, String message) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + projectId));
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + personId));
        Comment comment = new Comment(project, person, message, LocalDateTime.now(clock));

        person.getComments().add(comment);
        project.getComments().add(comment);
        return commentRepository.save(comment);
    }
    @Override
    public Optional<Comment> get(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void changeMessage(UUID id, String newMessage) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        comment.setMessage(newMessage);
        commentRepository.save(comment);
    }
}