package com.crowdevents.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PersonRepositoryServiceTest {
    @Mock
    private PersonRepository mockPersonRepository;
    @InjectMocks
    private PersonRepositoryService personService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_WithProperParams_ShouldCreateNewPerson() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.save(Mockito.any()))
                .thenReturn(mockPerson);

        Person result = personService.register("email", "password", "name");

        assertEquals(mockPerson, result);
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingPerson() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        Optional<Person> result = personService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockPerson), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Person> result = personService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeletePerson() {
        personService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockPersonRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeName_WithProperParams_ShouldChangeName() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertEquals("name", mockPerson.getName());
        personService.changeName(UUID.fromString("00000000-0000-0000-0000-000000000001"), "New name");
        assertEquals("New name", mockPerson.getName());
    }

    @Test
    public void changeName_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changeName(UUID.fromString("00000000-0000-0000-0000-000000000001"), "New name");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeSurname_WithProperParams_ShouldChangeSurname() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertNull(mockPerson.getSurname());
        personService.changeSurname(UUID.fromString("00000000-0000-0000-0000-000000000001"), "New surname");
        assertEquals("New surname", mockPerson.getSurname());
    }

    @Test
    public void changeSurname_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changeSurname(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New surname");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changePassword_WithProperParams_ShouldChangePassword() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertEquals("password", mockPerson.getPassword());
        personService.changePassword(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New password");
        assertEquals("New password", mockPerson.getPassword());
    }

    @Test
    public void changePassword_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changePassword(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New password");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeEmail_WithProperParams_ShouldChangeEmail() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertEquals("email", mockPerson.getEmail());
        personService.changeEmail(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "new@mail.com");
        assertEquals("new@mail.com", mockPerson.getEmail());
    }

    @Test
    public void changeEmail_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changePassword(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New password");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeCountry_WithProperParams_ShouldChangeCountry() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertNull(mockPerson.getCountry());
        personService.changeCountry(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New country");
        assertEquals("New country", mockPerson.getCountry());
    }

    @Test
    public void changeCountry_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changeCountry(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New country");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeCity_WithProperParams_ShouldChangeCity() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));

        assertNull(mockPerson.getCity());
        personService.changeCity(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New city");
        assertEquals("New city", mockPerson.getCity());
    }

    @Test
    public void changeCity_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.changeCity(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New city");
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}