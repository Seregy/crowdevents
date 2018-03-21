package com.crowdevents.person;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.UUID;

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
    public Person person(@PathVariable UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("No such person detected"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPerson(@RequestBody Person person, HttpServletRequest servletRequest) {
        Person createdPerson = personRepository.save(new Person(person.getEmail(), person.getPassword(), person.getName()));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/person/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
