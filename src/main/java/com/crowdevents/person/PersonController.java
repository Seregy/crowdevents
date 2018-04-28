package com.crowdevents.person;

import com.crowdevents.core.web.PageResource;
import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("v0/persons")
public class PersonController {
    private PersonService personService;
    private ModelMapper modelMapper;

    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with persons inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with persons
     */
    @JsonView(Views.Minimal.class)
    @GetMapping
    @ResponseBody
    public PageResource<PersonResource> getAllPersons(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber, limit);
        Page<Person> resultPage = personService.getAll(pageRequest);

        return new PageResource<>(
                resultPage.map((person) -> modelMapper.map(person, PersonResource.class)));
    }

    /**
     * Returns specific person.
     *
     * @param id id of the person to be returned
     * @return response with http status 204 with person inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<PersonResource> getPerson(@PathVariable("id") Long id) {
        return personService.get(id)
                .map(person -> ResponseEntity.ok(modelMapper.map(person, PersonResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Registers new person.
     *
     * @param newPerson person to be registered
     * @param servletRequest information about request
     * @return response with http status 201 and link to the person in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping
    public ResponseEntity registerPerson(@RequestBody PersonResource newPerson,
                                        HttpServletRequest servletRequest) {
        Person createdPerson = personService.register(newPerson.getEmail(),
                newPerson.getPassword(),
                newPerson.getName());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/persons/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing person.
     *
     * @param id id of person to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the person wasn't found
     */
    @JsonView(Views.Detailed.class)
    @PostMapping(value = "/{id}")
    public ResponseEntity updatePerson(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<Person> person = personService.get(id);
        if (person.isPresent()) {
            PersonResource personResource = modelMapper.map(person.get(), PersonResource.class);
            modelMapper.map(patchValues, personResource);
            personService.update(id, modelMapper.map(personResource, Person.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing person.
     *
     * @param id id of the person to delete
     * @return response with http status 204 or 404 if the person wasn't found
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id) {
        if (personService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
