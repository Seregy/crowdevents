package com.crowdevents.person;

import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Person> get(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void changeName(Long id, String newName) {
        Person person = getPerson(id);
        person.setName(newName);
        personRepository.save(person);
    }

    @Override
    public void changeSurname(Long id, String newSurname) {
        Person person = getPerson(id);
        person.setSurname(newSurname);
        personRepository.save(person);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        Person person = getPerson(id);
        person.setPassword(newPassword);
        personRepository.save(person);
    }

    @Override
    public void changeEmail(Long id, String newEmail) {
        Person person = getPerson(id);
        person.setEmail(newEmail);
        personRepository.save(person);
    }

    @Override
    public void changeCountry(Long id, String newCountry) {
        Person person = getPerson(id);
        person.setCountry(newCountry);
        personRepository.save(person);
    }

    @Override
    public void changeCity(Long id, String newCity) {
        Person person = getPerson(id);
        person.setCity(newCity);
        personRepository.save(person);
    }

    private Person getPerson(Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }
}
