package com.crowdevents.person;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonRepositoryService implements PersonService {
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository,
                                   PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Person register(String email, String password, String name) {

        Person person = new Person(email, passwordEncoder.encode(password), name);
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
    public void update(Long id, Person updatedPerson) {
        if (updatedPerson == null) {
            throw new IllegalArgumentException("Updated person must not be null");
        }

        Person person = getPerson(id);
        person.setName(updatedPerson.getName());
        person.setSurname(updatedPerson.getSurname());
        person.setEmail(updatedPerson.getEmail());
        person.setPassword(updatedPerson.getPassword());
        person.setCountry(updatedPerson.getCountry());
        person.setCity(updatedPerson.getCity());
        person.setPersonImageLink(updatedPerson.getPersonImageLink());
        person.setRole(updatedPerson.getRole());
        personRepository.save(person);
    }

    private Person getPerson(Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }
}
