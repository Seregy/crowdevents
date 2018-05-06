package com.crowdevents.update;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
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
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Update mockUpdate = new Update(mockProject, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.save(Mockito.any())).thenReturn(mockUpdate);

        Update result = updateService.post(1L, "Message");

        assertEquals(mockUpdate, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.save(Mockito.any())).thenReturn(mockUpdate);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.post(1L, "Message");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingUpdate() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.of(mockUpdate));

        Optional<Update> result = updateService.get(1L);

        assertEquals(Optional.of(mockUpdate), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Update> result = updateService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteUpdate() {
        Mockito.when(mockUpdateRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(updateService.delete(1L));

        Mockito.verify(mockUpdateRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeMessage() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.of(mockUpdate));

        assertEquals("Message", mockUpdate.getMessage());
        updateService.changeMessage(1L,
                "New message");
        assertEquals("New message", mockUpdate.getMessage());
    }

    @Test
    public void changeMessage_WithWrongUpdateId_ShouldThrowException() {
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.changeMessage(1L,
                    "New message");
        });

        assertEquals("Invalid update id: 1",
                exception.getMessage());
    }

    @Test
    public void changeShortMessage_WithProperParams_ShouldChangeShortMessage() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.of(mockUpdate));

        assertNull(mockUpdate.getShortMessage());
        updateService.changeShortMessage(1L,
                "New short message");
        assertEquals("New short message", mockUpdate.getShortMessage());
    }

    @Test
    public void changeShortMessage_WithWrongUpdateId_ShouldThrowException() {
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.changeShortMessage(1L,
                    "New short message");
        });

        assertEquals("Invalid update id: 1",
                exception.getMessage());
    }

    @Test
    public void update_WithProperParams_ShouldUpdateUpdate() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.of(mockUpdate));
        Update updatedUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"),
                "New message");
        updatedUpdate.setShortMessage("Short message");

        assertEquals("Message", mockUpdate.getMessage());
        assertNull(mockUpdate.getShortMessage());
        updateService.update(1L, updatedUpdate);
        assertEquals("New message", mockUpdate.getMessage());
        assertEquals("Short message", mockUpdate.getShortMessage());
    }

    @Test
    public void update_WithNullUpdate_ShouldThrowException() {
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Message");
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.of(mockUpdate));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.update(1L, null);
        });

        assertEquals("Updated update must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongUpdateId_ShouldThrowException() {
        Mockito.when(mockUpdateRepository.findById(1L))
                .thenReturn(Optional.empty());
        Update updatedUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"),
                "New message");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            updateService.update(1L, updatedUpdate);
        });

        assertEquals("Invalid update id: 1",
                exception.getMessage());
    }
}