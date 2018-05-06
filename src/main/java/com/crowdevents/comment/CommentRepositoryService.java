package com.crowdevents.comment;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
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
public class CommentRepositoryService implements CommentService {
    private CommentRepository commentRepository;
    private ProjectRepository projectRepository;
    private PersonRepository personRepository;
    private Clock clock;

    /**
     * Creates new comment service that uses repositories.
     *
     * @param commentRepository comment repository
     * @param projectRepository project repository
     * @param personRepository person repository
     * @param clock clock to use for generating date and time
     */
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
    public Comment post(Long projectId, Long personId, String message) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid project id: " + projectId));
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + personId));
        Comment comment = new Comment(project, person, message, LocalDateTime.now(clock));

        person.addComment(comment);
        project.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> get(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Page<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> getAllByProject(Long projectId, Pageable pageable) {
        return commentRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public Page<Comment> getAllByPerson(Long personId, Pageable pageable) {
        return commentRepository.findAllByAuthorId(personId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        }

        return !commentRepository.existsById(id);
    }

    @Override
    public void update(Long id, Comment updatedComment) {
        if (updatedComment == null) {
            throw new IllegalArgumentException("Updated comment must not be null");
        }

        Comment comment = getComment(id);
        comment.setMessage(updatedComment.getMessage());
        commentRepository.save(comment);
    }

    private Comment getComment(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
    }
}
