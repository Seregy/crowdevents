package com.crowdevents.comment;

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
public class CommentController {
    private CommentService commentService;
    private ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with comments inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with comments
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("comments")
    public PageResource<CommentResource> getAllComments(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Comment> commentPage = commentService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                commentPage.map((comment) -> modelMapper.map(comment, CommentResource.class)));
    }

    /**
     * Returns page with comments to specific project.
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with comments
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("projects/{projectId}/comments")
    public PageResource<CommentResource> getAllCommentsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Comment> commentPage = commentService.getAllByProject(projectId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                commentPage.map((comment) -> modelMapper.map(comment, CommentResource.class)));
    }

    /**
     * Returns page with comments written by specific person.
     *
     * @param personId id of the person
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with comments
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("persons/{personId}/comments")
    public PageResource<CommentResource> getAllCommentsByPerson(
            @PathVariable("personId") Long personId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Comment> commentPage = commentService.getAllByPerson(personId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                commentPage.map((comment) -> modelMapper.map(comment, CommentResource.class)));
    }

    /**
     * Returns specific comment.
     *
     * @param id id of the comment to be returned
     * @return response with http status 204 with comment inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "comments/{id}")
    public ResponseEntity<CommentResource> getComment(@PathVariable("id") Long id) {
        return commentService.get(id)
                .map(comment -> ResponseEntity.ok(
                        modelMapper.map(comment, CommentResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Posts new comment.
     *
     * @param newComment comment to be posted
     * @param servletRequest information about request
     * @return response with http status 201 and link to the comment in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("comments")
    public ResponseEntity postComment(@RequestBody CommentResource newComment,
                                         HttpServletRequest servletRequest) {
        Comment createdComment = commentService.post(newComment.getProject().getId(),
                newComment.getAuthor().getId(), newComment.getMessage());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/comments/{id}")
                .buildAndExpand(createdComment.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing comment.
     *
     * @param id id of the comment to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the comment wasn't found
     */
    @PostMapping("comments/{id}")
    public ResponseEntity updateComment(@PathVariable("id") Long id,
                                         @RequestBody Map<String, Object> patchValues) {
        Optional<Comment> comment = commentService.get(id);
        if (comment.isPresent()) {
            CommentResource commentResource = modelMapper.map(
                    comment.get(), CommentResource.class);
            modelMapper.map(patchValues, commentResource);
            commentService.update(id, modelMapper.map(commentResource, Comment.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing comment.
     *
     * @param id id of the comment to delete
     * @return response with http status 204 or 404 if the comment wasn't found
     */
    @DeleteMapping("comments/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Long id) {
        if (commentService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
