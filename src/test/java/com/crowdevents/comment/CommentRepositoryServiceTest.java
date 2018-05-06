package com.crowdevents.comment;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), mockPerson);
        Comment mockComment = new Comment(mockProject, mockPerson, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        Comment result = commentService.post(1L, 1L,
                "message");

        assertEquals(mockComment, result);
    }

    @Test
    public void post_WithWrongPersonId_ShouldThrowException() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Comment mockComment = new Comment(mockProject, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(mockProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.post(1L, 1L,
                    "message");
        });

        assertEquals("Invalid person id: 1",
                exception.getMessage());
    }

    @Test
    public void post_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Comment mockComment = new Comment(null, mockPerson, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.save(Mockito.any()))
                .thenReturn(mockComment);
        Mockito.when(mockPersonRepository.findById(1L)).thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.post(1L, 1L,
                    "message");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingComment() {
        Comment mockComment = new Comment(null, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.findById(1L)).thenReturn(Optional.of(mockComment));

        Optional<Comment> result = commentService.get(1L);

        assertEquals(Optional.of(mockComment), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockCommentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Comment> result = commentService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllComments() {
        Comment[] comments = {new Comment(null, null, "Comment 1", LocalDateTime.parse("2018-01-01T01:00:00")),
                    new Comment(null, null, "Comment 2", LocalDateTime.parse("2018-02-01T01:00:00")),
                    new Comment(null, null, "Comment 3", LocalDateTime.parse("2018-03-01T01:00:00")),
                    new Comment(null, null, "Comment 4", LocalDateTime.parse("2018-04-01T01:00:00"))};
        Mockito.when(mockCommentRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(comments)));

        Page<Comment> result = commentService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(comments)), result);
    }

    @Test
    public void getAllByProject_WithProperProjectId_ShouldReturnAllComments() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Comment[] comments = {new Comment(project, null, "Comment 1", LocalDateTime.parse("2018-01-01T01:00:00")),
                new Comment(project, null, "Comment 2", LocalDateTime.parse("2018-02-01T01:00:00"))};
        Mockito.when(mockCommentRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(comments)));

        Page<Comment> result = commentService.getAllByProject(1L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(comments)), result);
    }

    @Test
    public void getAllByProject_WithWrongProjectId_ShouldReturnEmptyPage() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Comment[] comments = {new Comment(project, null, "Comment 1", LocalDateTime.parse("2018-01-01T01:00:00")),
                new Comment(project, null, "Comment 2", LocalDateTime.parse("2018-02-01T01:00:00"))};
        Mockito.when(mockCommentRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(comments)));
        Mockito.when(mockCommentRepository.findAllByProjectId(Mockito.eq(2L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Comment> result = commentService.getAllByProject(2L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Collections.emptyList()), result);
    }

    @Test
    public void getAllByPerson_WithProperPersonId_ShouldReturnAllComments() {
        Person person = new Person("person1@email.com", "pass", "Person 1");
        person.setId(1L);
        Comment[] comments = {new Comment(null, person, "Comment 1", LocalDateTime.parse("2018-01-01T01:00:00")),
                new Comment(null, person, "Comment 2", LocalDateTime.parse("2018-02-01T01:00:00"))};
        Mockito.when(mockCommentRepository.findAllByAuthorId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(comments)));

        Page<Comment> result = commentService.getAllByPerson(1L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(comments)), result);
    }

    @Test
    public void getAllByPerson_WithWrongPersonId_ShouldReturnEmptyPage() {
        Person person = new Person("person1@email.com", "pass", "Person 1");
        person.setId(1L);
        Comment[] comments = {new Comment(null, person, "Comment 1", LocalDateTime.parse("2018-01-01T01:00:00")),
                new Comment(null, person, "Comment 2", LocalDateTime.parse("2018-02-01T01:00:00"))};
        Mockito.when(mockCommentRepository.findAllByAuthorId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(comments)));
        Mockito.when(mockCommentRepository.findAllByAuthorId(Mockito.eq(2L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Comment> result = commentService.getAllByPerson(2L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Collections.emptyList()), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        Mockito.when(mockCommentRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(commentService.delete(1L));

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithNewMessage_ShouldChangeMessage() {
        Comment mockComment = new Comment(null, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.findById(1L))
                .thenReturn(Optional.of(mockComment));
        Comment updatedComment = new Comment(null, null, "New message", null);

        commentService.update(1L, updatedComment);
        assertEquals("New message", mockComment.getMessage());
    }

    @Test
    public void update_WithNullUpdatedComment_ShouldThrowException() {
        Comment mockComment = new Comment(null, null, "Some message",
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockCommentRepository.findById(1L))
                .thenReturn(Optional.of(mockComment));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.update(1L, null);
        });

        assertEquals("Updated comment must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongCommentId_ShouldThrowException() {
        Mockito.when(mockCommentRepository.findById(1L))
                .thenReturn(Optional.empty());
        Comment updatedComment = new Comment(null, null, "New message", null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.update(1L, updatedComment);
        });

        assertEquals("Invalid comment id: 1",
                exception.getMessage());
    }
}