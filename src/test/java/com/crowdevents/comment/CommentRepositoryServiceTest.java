package com.crowdevents.comment;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.*;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CommentRepositoryServiceTest {
    @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private ProjectRepository mockProjectRepository;
    @Mock
    private PersonRepository mockPersonRepository;

    private CommentRepositoryService commentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Clock clock = Clock.fixed(Instant.parse("2018-01-01T01:00:00Z"), ZoneId.systemDefault());
        commentService = new CommentRepositoryService(mockCommentRepository, mockProjectRepository,
                mockPersonRepository, clock);
    }

    @Test
    public void post_WithProperParams_ShouldCreateComment() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Comment mockComment = new Comment(mockProject, mockPerson, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        Comment result = commentService.post(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "message");

        assertEquals(mockComment, result);
    }

    @Test
    public void post_WithWrongPersonId_ShouldThrowException() {
        Project mockProject = new Project("Name", "description", null);
        Comment mockComment = new Comment(mockProject, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.post(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "message");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void post_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Comment mockComment = new Comment(null, mockPerson, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.post(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "message");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingComment() {
        Comment mockComment = new Comment(null, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockComment));

        Optional<Comment> result = commentService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockComment), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockCommentRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Comment> result = commentService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        commentService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeComment() {
        Comment mockComment = new Comment(null, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockComment));

        assertEquals("Some message", mockComment.getMessage());
        commentService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New message");
        assertEquals("New message", mockComment.getMessage());
    }

    @Test
    public void changeMessage_WithWrongCommentId_ShouldThrowException() {
        Mockito.when(mockCommentRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "message");
        });

        assertEquals("Invalid comment id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}