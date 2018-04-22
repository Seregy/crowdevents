package com.crowdevents.project;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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

    /**
     * Returns page with projects inside it.
     *
     * <p>Projects inside the page are sorted by id and can be filtered by ids:
     * starting after specific id and/or ending before specific id.</p>
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @param startingAfter id of the project after which projects will be returned, exclusive
     * @param endingBefore id of the project before which projects will be returned, exclusive
     * @return page with projects
     */
    @JsonView(Views.Minimal.class)
    @GetMapping
    @ResponseBody
    public PageResource<ProjectResource> getAllProjects(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "starting_after", required = false) Long startingAfter,
            @RequestParam(name = "ending_before", required = false) Long endingBefore) {
        Page<Project> resultPage;
        PageRequest pageRequest = PageRequest.of(pageNumber, limit);
        if (startingAfter == null && endingBefore == null) {
            resultPage = projectService.getAll(pageRequest);
        } else {
            resultPage = projectService.getAllBeforeAndOrAfter(endingBefore,
                    startingAfter, pageRequest);
        }

        return new PageResource<>(
                resultPage.map((project) -> modelMapper.map(project, ProjectResource.class)));
    }

    /**
     * Returns specific project.
     *
     * @param id id of the project to be returned
     * @return project or {@code null} if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ProjectResource> getProject(@PathVariable("id") Long id) {
        return projectService.get(id)
                .map(project -> ResponseEntity.ok(modelMapper.map(project, ProjectResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates new project.
     *
     * @param newProject project to be created
     * @param servletRequest information about request
     * @return response with http status 201 and link to the project in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping
    public ResponseEntity createProject(@RequestBody ProjectResource newProject,
                                        HttpServletRequest servletRequest) {
        Project createdProject = projectService.create(newProject.getName(),
                newProject.getDescription(),
                newProject.getFundingGoal(), newProject.getOwners().toArray(new Long[] {}));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/projects/{id}")
                .buildAndExpand(createdProject.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Update existing project.
     *
     * @param id id of the project to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the project wasn't found
     */
    @JsonView(Views.Detailed.class)
    @PostMapping(value = "/{id}")
    public ResponseEntity updateProject(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
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
