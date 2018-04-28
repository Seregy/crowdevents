package com.crowdevents.message;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
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

public class MessageRepositoryServiceTest {
    @Mock
    private MessageRepository mockMessageRepository;
    @Mock
    private PersonRepository mockPersonRepository;

    private MessageRepositoryService messageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Clock clock = Clock.fixed(Instant.parse("2018-01-01T01:00:00Z"), ZoneId.systemDefault());
        messageService = new MessageRepositoryService(mockMessageRepository, mockPersonRepository, clock);
    }

    @Test
    public void send_WithProperParams_ShouldCreateNewMessage() {
        Person mockSender = new Person("sender@mail.com", "senderpass", "sender");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockSender));
        Person mockReceiver = new Person("receiver@mail.com", "receiverpass", "receiver");
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.of(mockReceiver));
        Message mockMessage = new Message("Message", mockSender, mockReceiver,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.save(Mockito.any())).thenReturn(mockMessage);

        Message result = messageService.send(1L,
                2L, "Message");

        assertEquals(mockMessage, result);
    }

    @Test
    public void send_WithWrongSenderId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person mockReceiver = new Person("receiver@mail.com", "receiverpass", "receiver");
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.of(mockReceiver));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.send(1L,
                    2L, "Message");
        });

        assertEquals("Invalid person id: 1",
                exception.getMessage());
    }

    @Test
    public void send_WithWrongReceiverId_ShouldThrowException() {
        Person mockSender = new Person("sender@mail.com", "senderpass", "sender");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockSender));
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.send(1L,
                    2L, "Message");
        });

        assertEquals("Invalid person id: 2",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingMessage() {
        Message mockMessage = new Message("Message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.of(mockMessage));

        Optional<Message> result = messageService.get(1L);

        assertEquals(Optional.of(mockMessage), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Message> result = messageService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        Mockito.when(mockMessageRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(messageService.delete(1L));

        Mockito.verify(mockMessageRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeMessage() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.of(mockMessage));

        assertEquals("Mock message", mockMessage.getMessage());
        messageService.changeMessage(1L,
                "New message");
        assertEquals("New message", mockMessage.getMessage());
    }

    @Test
    public void changeMessage_WithWrongMessageId_ShouldThrowException() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.changeMessage(1L,
                    "New message");
        });

        assertEquals("Invalid message id: 1",
                exception.getMessage());
        assertEquals("Mock message", mockMessage.getMessage());
    }

    @Test
    public void update_WithProperParams_ShouldUpdateMessage() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.of(mockMessage));
        Message updatedMessage = new Message("New message", null, null, null);

        assertEquals("Mock message", mockMessage.getMessage());
        messageService.update(1L, updatedMessage);
        assertEquals("New message", mockMessage.getMessage());
    }

    @Test
    public void update_WithNullUpdatedMessage_ShouldThrowException() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.of(mockMessage));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.update(1L, null);
        });

        assertEquals("Updated message must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongMessageId_ShouldThrowException() {
        Mockito.when(mockMessageRepository.findById(1L))
                .thenReturn(Optional.empty());
        Message updatedMessage = new Message("New message", null, null, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.update(1L, updatedMessage);
        });

        assertEquals("Invalid message id: 1",
                exception.getMessage());
    }
}