package com.crowdevents.person;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Person register(String email, String password, String name);

    Optional<Person> get(Long id);

    Page<Person> getAll(Pageable pageable);

    boolean delete(Long id);

    void changeName(Long id, String newName);

    void changeSurname(Long id, String newSurname);

    void changePassword(Long id, String newPassword);

    void changeEmail(Long id, String newEmail);

    void changeCountry(Long id, String newCountry);

    void changeCity(Long id, String newCity);

    void update(Long id, Person newPerson);
}
