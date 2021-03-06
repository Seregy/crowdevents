package com.crowdevents.person;

import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Detailed.class)
public class PersonResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonView(Views.Minimal.class)
    private String name;

    @JsonView(Views.Minimal.class)
    private String surname;

    @JsonView(Views.Protected.class)
    private String password;

    private String email;
    private String country;
    private String city;

    @JsonProperty("image_link")
    @JsonView(Views.Minimal.class)
    private String personImageLink;

    private PersonRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPersonImageLink() {
        return personImageLink;
    }

    public void setPersonImageLink(String personImageLink) {
        this.personImageLink = personImageLink;
    }

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
    }
}
