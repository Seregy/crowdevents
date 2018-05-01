package com.crowdevents.notification;

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
public class NotificationController {
    private NotificationService notificationService;
    private ModelMapper modelMapper;

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  ModelMapper modelMapper) {
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with notifications inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with notifications
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("notifications")
    public PageResource<? extends BaseNotificationResource> getAllNotifications(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<BaseNotification> notificationPage = notificationService.getAll(
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                notificationPage.map((notification) -> modelMapper.map(
                        notification, BaseNotificationResource.class)));
    }

    /**
     * Returns page with notifications sent to specific person.
     *
     * @param personId id of the person
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with notifications
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("persons/{personId}/notifications")
    public PageResource<? extends BaseNotificationResource> getAllNotificationsByPerson(
            @PathVariable("personId") Long personId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<? extends BaseNotification> notificationPage = notificationService.getAllByPerson(
                personId, PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                notificationPage.map((notification) -> modelMapper.map(
                        notification, BaseNotificationResource.class)));
    }

    /**
     * Returns specific notification.
     *
     * @param id id of the notification to be returned
     * @return response with http status 204 with notification inside the
     *      body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "notifications/{id}")
    public ResponseEntity<? extends BaseNotificationResource> getNotification(
            @PathVariable("id") Long id) {
        Optional<? extends BaseNotification> optionalNotification =  notificationService.get(id);
        return optionalNotification
                .map(notification -> ResponseEntity.ok(
                        modelMapper.map(notification, BaseNotificationResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Sends new notification.
     *
     * @param newNotification notification to be posted
     * @param servletRequest information about request
     * @return response with http status 201 and link to the notification in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("notifications")
    public ResponseEntity sendNotification(@RequestBody BaseNotificationResource newNotification,
                                      HttpServletRequest servletRequest) {
        BaseNotification sentNotification = sendGenericNotification(newNotification);

        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/notifications/{id}")
                .buildAndExpand(sentNotification.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing notification.
     *
     * @param id id of the notification to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the notification wasn't found
     */
    @PostMapping("notifications/{id}")
    public ResponseEntity updateNotification(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<BaseNotification> optionalNotification = notificationService.get(id);
        if (optionalNotification.isPresent()) {
            BaseNotificationResource notificationResource = modelMapper.map(
                    optionalNotification.get(), BaseNotificationResource.class);
            modelMapper.map(patchValues, notificationResource);
            notificationService.update(id, modelMapper.map(
                    notificationResource, BaseNotification.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing notification.
     *
     * @param id id of the notification to delete
     * @return response with http status 204 or 404 if the notification wasn't found
     */
    @DeleteMapping("notifications/{id}")
    public ResponseEntity deleteNotification(@PathVariable("id") Long id) {
        if (notificationService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private  <T extends BaseNotificationResource> BaseNotification sendGenericNotification(
            T newNotification) {
        if (newNotification instanceof ContributionNotificationResource) {
            ContributionNotificationResource notification =
                    (ContributionNotificationResource) newNotification;
            return notificationService.sendContributionNotification(notification.getMessage(),
                    notification.getContribution().getId(), notification.getReceiver().getId(),
                    notification.getProject().getId());
        } else if (newNotification instanceof PersonNotificationResource) {
            PersonNotificationResource notification = (PersonNotificationResource) newNotification;
            return notificationService.sendPersonNotification(notification.getMessage(),
                    notification.getPerson().getId(), notification.getReceiver().getId(),
                    notification.getProject().getId());
        } else if (newNotification instanceof UpdateNotificationResource) {
            UpdateNotificationResource notification = (UpdateNotificationResource) newNotification;
            return notificationService.sendUpdateNotification(notification.getMessage(),
                    notification.getUpdateResource().getId(), notification.getReceiver().getId(),
                    notification.getProject().getId());
        } else {
            return notificationService.sendBaseNotification(newNotification.getMessage(),
                    newNotification.getReceiver().getId(),
                    newNotification.getProject().getId());
        }
    }
}
