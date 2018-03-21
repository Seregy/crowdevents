package com.crowdevents.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PersonRepositoryService implements PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person register(String email, String password, String name) {
        Person person = new Person(email, password, name);
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> get(UUID id) {
        return personRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    @Override
    public void changeName(UUID id, String newName) {
        Person person = getPerson(id);
        person.setName(newName);
        personRepository.save(person);
    }

    @Override
    public void changeSurname(UUID id, String newSurname) {
        Person person = getPerson(id);
        person.setSurname(newSurname);
        personRepository.save(person);
    }

    @Override
    public void changePassword(UUID id, String newPassword) {
        Person person = getPerson(id);
        person.setPassword(newPassword);
        personRepository.save(person);
    }

    @Override
    public void changeEmail(UUID id, String newEmail) {
        Person person = getPerson(id);
        person.setEmail(newEmail);
        personRepository.save(person);
    }

    @Override
    public void changeCountry(UUID id, String newCountry) {
        Person person = getPerson(id);
        person.setCountry(newCountry);
        personRepository.save(person);
    }

    @Override
    public void changeCity(UUID id, String newCity) {
        Person person = getPerson(id);
        person.setCity(newCity);
        personRepository.save(person);
    }

    private Person getPerson(UUID id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid person id: " + id));
    }
}
