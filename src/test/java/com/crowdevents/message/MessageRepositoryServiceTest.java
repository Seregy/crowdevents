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
import java.util.UUID;

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
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockSender));
        Person mockReceiver = new Person("receiver@mail.com", "receiverpass", "receiver");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockReceiver));
        Message mockMessage = new Message("Message", mockSender, mockReceiver,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.save(Mockito.any())).thenReturn(mockMessage);

        Message result = messageService.send(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"), "Message");

        assertEquals(mockMessage, result);
    }

    @Test
    public void send_WithWrongSenderId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Person mockReceiver = new Person("receiver@mail.com", "receiverpass", "receiver");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockReceiver));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.send(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"), "Message");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void send_WithWrongReceiverId_ShouldThrowException() {
        Person mockSender = new Person("sender@mail.com", "senderpass", "sender");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockSender));
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.send(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"), "Message");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingMessage() {
        Message mockMessage = new Message("Message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockMessage));

        Optional<Message> result = messageService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockMessage), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockMessageRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Message> result = messageService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        messageService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockMessageRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeMessage() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockMessage));

        assertEquals("Mock message", mockMessage.getMessage());
        messageService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New message");
        assertEquals("New message", mockMessage.getMessage());
    }

    @Test
    public void changeMessage_WithWrongMessageId_ShouldThrowException() {
        Message mockMessage = new Message("Mock message", null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockMessageRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            messageService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New message");
        });

        assertEquals("Invalid message id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
        assertEquals("Mock message", mockMessage.getMessage());
    }
}