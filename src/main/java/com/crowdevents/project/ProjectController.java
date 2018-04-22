package com.crowdevents.project;

import com.crowdevents.core.web.Views;
import com.crowdevents.core.web.PageResource;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("v0/projects")
@CrossOrigin
public class ProjectController {
    private ProjectService projectService;
    private ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ModelMapper modelMapper) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @JsonView(Views.Minimal.class)
    @GetMapping
    @ResponseBody
    public PageResource<ProjectResource> getAllProjects(@RequestParam(name = "page", defaultValue = "0")
                                                                int pageNumber,
                                                        @RequestParam(name = "limit", defaultValue = "10")
                                                                int limit,
                                                        @RequestParam(name = "starting_after", required = false)
                                                                Long startingAfter,
                                                        @RequestParam(name = "ending_before", required = false)
                                                                Long endingBefore) {
        Page<Project> resultPage;
        PageRequest pageRequest = PageRequest.of(pageNumber, limit);
        if (startingAfter == null && endingBefore == null) {
            resultPage = projectService.getAll(pageRequest);
        } else {
            resultPage = projectService.getAllBeforeAndOrAfter(endingBefore, startingAfter, pageRequest);
        }

        return new PageResource<>(resultPage.map((project) -> modelMapper.map(project, ProjectResource.class)));
    }

    @JsonView(Views.Detailed.class)
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ProjectResource> getProject(@PathVariable("id") Long id) {
        return projectService.get(id)
                .map(project -> ResponseEntity.ok(modelMapper.map(project, ProjectResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @JsonView(Views.Detailed.class)
    @PostMapping
    public ResponseEntity createProject(@RequestBody ProjectResource newProject, HttpServletRequest servletRequest) {
        Project createdProject = projectService.create(newProject.getName(), newProject.getDescription(),
                newProject.getFundingGoal(), newProject.getOwners().toArray(new Long[] {}));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/projects/{id}")
                .buildAndExpand(createdProject.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @JsonView(Views.Detailed.class)
    @PostMapping(value = "/{id}")
    public ResponseEntity updateProject(@PathVariable("id") Long id, @RequestBody Map<String, Object> patchValues) {
        Optional<Project> project = projectService.get(id);
        if (project.isPresent()) {
            ProjectResource projectResource = modelMapper.map(project.get(), ProjectResource.class);
            modelMapper.map(patchValues, projectResource);
            projectService.update(id, modelMapper.map(projectResource, Project.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
