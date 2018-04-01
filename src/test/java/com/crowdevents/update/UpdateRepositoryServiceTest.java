package com.crowdevents.update;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateRepositoryServiceTest {
    @Mock
    private UpdateRepository mockUpdateRepository;
    @Mock
    private ProjectRepository mockProjectRepository;
    private UpdateRepositoryService updateService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Clock clock = Clock.fixed(Instant.parse("2018-01-01T01:00:00Z"), ZoneId.systemDefault());
        updateService = new UpdateRepositoryService(mockUpdateRepository, mockProjectRepository, clock);
    }

    @Test
    public void post_WithProperParams_ShouldCreateNewUpdate() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));
        Update mockUpdate = new Update(mockProject, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.save(Mockito.any())).thenReturn(mockUpdate);

        Update result = updateService.post(UUID.fromString("00000000-0000-0000-0000-000000000001"), "Message");

        assertEquals(mockUpdate, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.save(Mockito.any())).thenReturn(mockUpdate);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.post(UUID.fromString("00000000-0000-0000-0000-000000000001"), "Message");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingUpdate() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockUpdate));

        Optional<Update> result = updateService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockUpdate), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Update> result = updateService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteUpdate() {
        updateService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockUpdateRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeMessage() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockUpdate));

        assertEquals("Message", mockUpdate.getMessage());
        updateService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New message");
        assertEquals("New message", mockUpdate.getMessage());
    }

    @Test
    public void changeMessage_WithWrongUpdateId_ShouldThrowException() {
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New message");
        });

        assertEquals("Invalid update id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeShortMessage_WithProperParams_ShouldChangeShortMessage() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockUpdate));

        assertNull(mockUpdate.getShortMessage());
        updateService.changeShortMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New short message");
        assertEquals("New short message", mockUpdate.getShortMessage());
    }

    @Test
    public void changeShortMessage_WithWrongUpdateId_ShouldThrowException() {
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.changeShortMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New short message");
        });

        assertEquals("Invalid update id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}