package com.crowdevents.person;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Person register(String email, String password, String name);

    Optional<Person> get(Long id);

    Page<Person> getAll(Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Person updatedPerson);
}
