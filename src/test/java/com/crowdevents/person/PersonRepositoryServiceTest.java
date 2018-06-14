package com.crowdevents.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class PersonRepositoryServiceTest {
    @Mock
    private PersonRepository mockPersonRepository;

    private PersonRepositoryService personService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        PasswordEncoder passwordEncoder = new AbstractPasswordEncoder() {
            @Override
            protected byte[] encode(CharSequence rawPassword, byte[] salt) {
                return  rawPassword.toString().getBytes();
            }
        };
        personService = new PersonRepositoryService(mockPersonRepository, passwordEncoder);
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
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));

        Optional<Person> result = personService.get(1L);

        assertEquals(Optional.of(mockPerson), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Person> result = personService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllPersons() {
        Person[] persons = {new Person("person1@mail.com", "pass1", "Person 1"),
                new Person("person2@mail.com", "pass2", "Person 2"),
                new Person("person3@mail.com", "pass3", "Person 3"),
                new Person("person4@mail.com", "pass4", "Person 4")};
        Mockito.when(mockPersonRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(persons)));

        Page<Person> result = personService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(persons)), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeletePerson() {
        Mockito.when(mockPersonRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(personService.delete(1L));

        Mockito.verify(mockPersonRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithNewName_ShouldChangeName() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "password", "New name");

        assertEquals("Name", mockPerson.getName());
        personService.update(1L, newPerson);
        assertEquals("New name", mockPerson.getName());
    }

    @Test
    public void update_WithNewSurname_ShouldChangeSurname() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "password", "Name");
        newPerson.setSurname("New surname");

        assertNull(mockPerson.getSurname());
        personService.update(1L, newPerson);
        assertEquals("New surname", mockPerson.getSurname());
    }

    @Test
    public void update_WithNewPassword_ShouldChangePassword() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "New password", "Name");

        assertEquals("password", mockPerson.getPassword());
        personService.update(1L, newPerson);
        assertEquals("New password", mockPerson.getPassword());
    }

    @Test
    public void update_WithNewEmail_ShouldChangeEmail() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("new@email.com", "password", "Name");

        assertEquals("name@email.com", mockPerson.getEmail());
        personService.update(1L, newPerson);
        assertEquals("new@email.com", mockPerson.getEmail());
    }

    @Test
    public void update_WithNewCountry_ShouldChangeCountry() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        mockPerson.setCountry("Country");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "password", "Name");
        newPerson.setCountry("New country");

        assertEquals("Country", mockPerson.getCountry());
        personService.update(1L, newPerson);
        assertEquals("New country", mockPerson.getCountry());
    }

    @Test
    public void update_WithNewCity_ShouldChangeCity() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        mockPerson.setCity("City");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "password", "Name");
        newPerson.setCity("New city");

        assertEquals("City", mockPerson.getCity());
        personService.update(1L, newPerson);
        assertEquals("New city", mockPerson.getCity());
    }

    @Test
    public void update_WithNewRole_ShouldChangeRole() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        mockPerson.setRole(PersonRole.USER);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Person newPerson = new Person("name@email.com", "password", "Name");
        newPerson.setRole(PersonRole.ADMIN);

        assertEquals(PersonRole.USER, mockPerson.getRole());
        personService.update(1L, newPerson);
        assertEquals(PersonRole.ADMIN, mockPerson.getRole());
    }

    @Test
    public void update_WithNullUpdatedPerson_ShouldThrowException() {
        Person mockPerson = new Person("name@email.com", "password", "Name");
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.update(1L, null);
        });

        assertEquals("Updated person must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person newPerson = new Person("new@email.com", "New password", null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.update(1L, newPerson);
        });

        assertEquals("Invalid person id: 1",
                exception.getMessage());
    }
}