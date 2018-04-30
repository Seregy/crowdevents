package com.crowdevents.reward;

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
public class RewardController {
    private RewardService rewardService;
    private ModelMapper modelMapper;

    @Autowired
    public RewardController(RewardService rewardService, ModelMapper modelMapper) {
        this.rewardService = rewardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with all rewards inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with rewards
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("rewards")
    public PageResource<RewardResource> getAllRewards(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Reward> rewardPage = rewardService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                rewardPage.map((reward) -> modelMapper.map(reward, RewardResource.class)));
    }

    /**
     * Returns page with all rewards in the specific project.
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with rewards
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("projects/{projectId}/rewards")
    public PageResource<RewardResource> getAllRewardsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Reward> rewardPage = rewardService.getAllByProject(projectId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                rewardPage.map((reward) -> modelMapper.map(reward, RewardResource.class)));
    }

    /**
     * Returns specific reward.
     *
     * @param id id of the reward to be returned
     * @return response with http status 204 with reward inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "rewards/{id}")
    public ResponseEntity<RewardResource> getReward(@PathVariable("id") Long id) {
        return rewardService.get(id)
                .map(reward -> ResponseEntity.ok(
                        modelMapper.map(reward, RewardResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates new reward.
     *
     * @param newReward reward to be created
     * @param servletRequest information about request
     * @return response with http status 201 and link to the reward in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("rewards")
    public ResponseEntity createReward(@RequestBody RewardResource newReward,
                                     HttpServletRequest servletRequest) {
        Reward createdReward = rewardService.create(newReward.getProject().getId(),
                newReward.getMaximumAmount(), newReward.getMinimalContribution(),
                newReward.getDescription());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/rewards/{id}")
                .buildAndExpand(createdReward.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing reward.
     *
     * @param id id of the reward to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the reward wasn't found
     */
    @PostMapping("rewards/{id}")
    public ResponseEntity updateReward(@PathVariable("id") Long id,
                                             @RequestBody Map<String, Object> patchValues) {
        Optional<Reward> reward = rewardService.get(id);
        if (reward.isPresent()) {
            RewardResource rewardResource = modelMapper.map(
                    reward.get(), RewardResource.class);
            modelMapper.map(patchValues, rewardResource);
            rewardService.update(id, modelMapper.map(rewardResource, Reward.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing reward.
     *
     * @param id id of the reward to delete
     * @return response with http status 204 or 404 if the reward wasn't found
     */
    @DeleteMapping("rewards/{id}")
    public ResponseEntity deleteReward(@PathVariable("id") Long id) {
        if (rewardService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
