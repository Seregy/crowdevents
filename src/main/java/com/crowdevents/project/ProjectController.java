package com.crowdevents.project;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
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
    public Page<ProjectResource> getAllProjects(@PageableDefault(value = 30) Pageable pageable,
                                                  @RequestAttribute(name = "starting_after",
                                                required = false) UUID startingAfterId) {
        return projectService.getAll(pageable)
                .map((project) -> modelMapper.map(project, ProjectResource.class));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addProject(@RequestBody ProjectResource project, HttpServletRequest servletRequest) {
        Project newProject = projectService.create(project.getName(), project.getDescription(),
                project.getFundingGoal(), project.getOwners().toArray(new UUID[] {}));
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/projects/{id}")
                .buildAndExpand(newProject.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
