package com.crowdevents.update;

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
public class UpdateController {
    private UpdateService updateService;
    private ModelMapper modelMapper;

    @Autowired
    public UpdateController(UpdateService updateService, ModelMapper modelMapper) {
        this.updateService = updateService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with all updates inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with updates
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("updates")
    public PageResource<UpdateResource> getAllUpdates(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Update> updatePage = updateService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                updatePage.map((update) -> modelMapper.map(update, UpdateResource.class)));
    }

    /**
     * Returns page with updates to specific project.
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with updates
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("projects/{projectId}/updates")
    public PageResource<UpdateResource> getAllUpdatesByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Update> updatePage = updateService.getAllByProject(projectId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                updatePage.map((update) -> modelMapper.map(update, UpdateResource.class)));
    }

    /**
     * Returns specific update.
     *
     * @param id id of the update to be returned
     * @return response with http status 204 with update inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "updates/{id}")
    public ResponseEntity<UpdateResource> getUpdate(@PathVariable("id") Long id) {
        return updateService.get(id)
                .map(update -> ResponseEntity.ok(
                        modelMapper.map(update, UpdateResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Posts new update.
     *
     * @param newUpdate update to be posted
     * @param servletRequest information about request
     * @return response with http status 201 and link to the update in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("updates")
    public ResponseEntity postUpdate(@RequestBody UpdateResource newUpdate,
                                    HttpServletRequest servletRequest) {
        Update postedUpdate = updateService.post(newUpdate.getProject().getId(),
                newUpdate.getMessage());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/updates/{id}")
                .buildAndExpand(postedUpdate.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing update.
     *
     * @param id id of the update to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the update wasn't found
     */
    @PostMapping("updates/{id}")
    public ResponseEntity updateUpdate(@PathVariable("id") Long id,
                                    @RequestBody Map<String, Object> patchValues) {
        Optional<Update> updateOptional = updateService.get(id);
        if (updateOptional.isPresent()) {
            UpdateResource updateResource = modelMapper.map(
                    updateOptional.get(), UpdateResource.class);
            modelMapper.map(patchValues, updateResource);
            updateService.update(id, modelMapper.map(updateResource, Update.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing update.
     *
     * @param id id of the update to delete
     * @return response with http status 204 or 404 if the update wasn't found
     */
    @DeleteMapping("updates/{id}")
    public ResponseEntity deleteUpdate(@PathVariable("id") Long id) {
        if (updateService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
