package com.crowdevents.message;

import com.crowdevents.core.web.PageResource;
import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("v0/")
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with messages inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with messages
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("messages")
    public PageResource<MessageResource> getAllMessages(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Message> messagePage = messageService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                messagePage.map((message) -> modelMapper.map(message, MessageResource.class)));
    }

    /**
     * Returns page with messages written to or from specific person.
     *
     * @param personId id of the person
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with messages
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("persons/{personId}/messages")
    public PageResource<MessageResource> getAllMessagesByPerson(
            @PathVariable("personId") Long personId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Message> messagePage = messageService.getAllByPerson(personId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                messagePage.map((message) -> modelMapper.map(message, MessageResource.class)));
    }

    /**
     * Returns specific message.
     *
     * @param id id of the message to be returned
     * @return response with http status 204 with message inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "messages/{id}")
    public ResponseEntity<MessageResource> getMessage(@PathVariable("id") Long id) {
        return messageService.get(id)
                .map(message -> ResponseEntity.ok(
                        modelMapper.map(message, MessageResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Sends new message.
     *
     * @param newMessage message to be sent
     * @param servletRequest information about request
     * @return response with http status 201 and link to the message in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("messages")
    public ResponseEntity sendMessage(@RequestBody MessageResource newMessage,
                                      HttpServletRequest servletRequest) {
        Message sentMessage = messageService.send(newMessage.getSender().getId(),
                newMessage.getReceiver().getId(), newMessage.getMessage());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/messages/{id}")
                .buildAndExpand(sentMessage.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing message.
     *
     * @param id id of the message to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the message wasn't found
     */
    @PostMapping("messages/{id}")
    public ResponseEntity updateMessage(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<Message> optionalMessage = messageService.get(id);
        if (optionalMessage.isPresent()) {
            MessageResource messageResource = modelMapper.map(
                    optionalMessage.get(), MessageResource.class);
            modelMapper.map(patchValues, messageResource);
            messageService.update(id, modelMapper.map(messageResource, Message.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing message.
     *
     * @param id id of the message to delete
     * @return response with http status 204 or 404 if the message wasn't found
     */
    @DeleteMapping("messages/{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") Long id) {
        if (messageService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
