package com.crowdevents.project;

import com.crowdevents.core.web.PageResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {
    private ProjectService projectService;
    private ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ModelMapper modelMapper) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ProjectResource> getAllProjects(@PageableDefault(value = 30) Pageable pageable,
                                                        @RequestAttribute(name = "starting_after",
                                                                required = false) Long startingAfterId) {
        return new PageResource<>(
                projectService.getAll(pageable)
                        .map((project) -> modelMapper.map(project, ProjectResource.class)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addProject(@RequestBody ProjectResource project, HttpServletRequest servletRequest) {
        Project newProject = projectService.create(project.getName(), project.getDescription(),
                project.getFundingGoal(), project.getOwners().toArray(new Long[] {}));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/projects/{id}")
                .buildAndExpand(newProject.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
