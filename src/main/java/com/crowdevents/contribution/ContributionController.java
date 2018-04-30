package com.crowdevents.contribution;

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
public class ContributionController {
    private ContributionService contributionService;
    private ModelMapper modelMapper;

    @Autowired
    public ContributionController(ContributionService contributionService,
                                  ModelMapper modelMapper) {
        this.contributionService = contributionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with all contributions inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with contributions
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("contributions")
    public PageResource<ContributionResource> getAllContributions(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Contribution> contributionPage =
                contributionService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                contributionPage.map((contribution) -> modelMapper.map(contribution,
                        ContributionResource.class)));
    }

    /**
     * Returns page with all contributions that were made to the specific project.
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with contributions
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("projects/{projectId}/contributions")
    public PageResource<ContributionResource> getAllContributionsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Contribution> contributionPage =
                contributionService.getAllByProject(projectId, PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                contributionPage.map((contribution) -> modelMapper.map(contribution,
                        ContributionResource.class)));
    }

    /**
     * Returns page with all contributions that were made by the specific person.
     *
     * @param personId id of the person
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with contributions
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("persons/{personId}/contributions")
    public PageResource<ContributionResource> getAllContributionsByPerson(
            @PathVariable("personId") Long personId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Contribution> contributionPage =
                contributionService.getAllByProject(personId, PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                contributionPage.map((contribution) -> modelMapper.map(contribution,
                        ContributionResource.class)));
    }

    /**
     * Returns specific contribution.
     *
     * @param id id of the contribution to be returned
     * @return response with http status 204 with contribution inside
     *      the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "contributions/{id}")
    public ResponseEntity<ContributionResource> getContribution(@PathVariable("id") Long id) {
        return contributionService.get(id)
                .map(contribution -> ResponseEntity.ok(
                        modelMapper.map(contribution, ContributionResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Makes new contribution.
     *
     * @param newContribution contribution to be made
     * @param servletRequest information about request
     * @return response with http status 201 and link to the contribution in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("contributions")
    public ResponseEntity contribute(@RequestBody ContributionResource newContribution,
                                      HttpServletRequest servletRequest) {
        Contribution createdContribution = contributionService.contribute(
                newContribution.getContributor().getId(), newContribution.getProject().getId(),
                newContribution.getMoney(), newContribution.getReward().getId());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/contributions/{id}")
                .buildAndExpand(createdContribution.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing contribution.
     *
     * @param id id of the contribution to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the contribution wasn't found
     */
    @PostMapping("contributions/{id}")
    public ResponseEntity updateContribution(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<Contribution> contribution = contributionService.get(id);
        if (contribution.isPresent()) {
            ContributionResource contributionResource = modelMapper.map(
                    contribution.get(), ContributionResource.class);
            modelMapper.map(patchValues, contributionResource);
            contributionService.update(id,
                    modelMapper.map(contributionResource, Contribution.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing contribution.
     *
     * @param id id of the contribution to delete
     * @return response with http status 204 or 404 if the contribution wasn't found
     */
    @DeleteMapping("contributions/{id}")
    public ResponseEntity deleteContribution(@PathVariable("id") Long id) {
        if (contributionService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
