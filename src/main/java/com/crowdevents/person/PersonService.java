package com.crowdevents.person;

import java.util.UUID;

public interface PersonService {
    Person register(String email, String password, String name);
    Person get(UUID id);
    void delete(UUID id);
    void changeName(UUID id, String newName);
    void changeSurname(UUID id, String newSurname);
    void changePassword(UUID id, String newPassword);
    void changeEmail(UUID id, String newEmail);
    void changeCountry(UUID id, String newCountry);
    void changeCity(UUID id, String newCity);
}
