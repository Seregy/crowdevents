package com.crowdevents.person;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/person")
public class PersonController {
    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Person> allPersons() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Person person(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No such person detected"));
    }

    /**
     * Adds new person.
     *
     * @param person person to add
     * @param servletRequest information about request
     * @return http status 201 Created with location of the person in header
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPerson(@RequestBody Person person, HttpServletRequest servletRequest) {
        Person createdPerson = personRepository.save(new Person(person.getEmail(),
                person.getPassword(), person.getName()));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/person/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
