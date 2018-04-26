package com.crowdevents.person;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }

        return !personRepository.existsById(id);
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

    @Override
    public void update(Long id, Person newPerson) {
        if (newPerson == null) {
            throw new IllegalArgumentException("NewPerson must not be null");
        }

        Person person = getPerson(id);
        person.setName(newPerson.getName());
        person.setSurname(newPerson.getSurname());
        person.setEmail(newPerson.getEmail());
        person.setPassword(newPerson.getPassword());
        person.setCountry(newPerson.getCountry());
        person.setCity(newPerson.getCity());
        personRepository.save(person);
    }

    private Person getPerson(Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }
}
