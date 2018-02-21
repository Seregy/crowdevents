package com.crowdevents.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User user(@PathVariable UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user detected"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity allUsers(@RequestBody User user, HttpServletRequest servletRequest) {
        User createdUser = userRepository.save(new User(user.getEmail(), user.getPassword(), user.getName(), user.getSurname()));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/user/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
