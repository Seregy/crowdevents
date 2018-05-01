package com.crowdevents.faq;

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
public class FaqController {
    private FaqService faqService;
    private ModelMapper modelMapper;

    @Autowired
    public FaqController(FaqService faqService, ModelMapper modelMapper) {
        this.faqService = faqService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with faqs inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with faqs
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("faqs")
    public PageResource<FaqResource> getAllFaqs(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Faq> faqPage = faqService.getAll(PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                faqPage.map((faq) -> modelMapper.map(faq, FaqResource.class)));
    }

    /**
     * Returns page with faqs to specific project.
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with faqs
     */
    @JsonView(Views.Minimal.class)
    @GetMapping("projects/{projectId}/faqs")
    public PageResource<FaqResource> getAllFaqsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<Faq> faqPage = faqService.getAllByProject(projectId,
                PageRequest.of(pageNumber, limit));
        return new PageResource<>(
                faqPage.map((faq) -> modelMapper.map(faq, FaqResource.class)));
    }

    /**
     * Returns specific faq.
     *
     * @param id id of the faq to be returned
     * @return response with http status 204 with faq inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "faqs/{id}")
    public ResponseEntity<FaqResource> getFaq(@PathVariable("id") Long id) {
        return faqService.get(id)
                .map(faq -> ResponseEntity.ok(
                        modelMapper.map(faq, FaqResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates new faq.
     *
     * @param newFaq faq to be created
     * @param servletRequest information about request
     * @return response with http status 201 and link to the faq in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping("faqs")
    public ResponseEntity createFaq(@RequestBody FaqResource newFaq,
                                      HttpServletRequest servletRequest) {
        Faq createdFaq = faqService.create(newFaq.getProject().getId(),
                newFaq.getQuestion(), newFaq.getAnswer());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/faqs/{id}")
                .buildAndExpand(createdFaq.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing faqs.
     *
     * @param id id of the faq to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the faq wasn't found
     */
    @PostMapping("faqs/{id}")
    public ResponseEntity updateFaq(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<Faq> faqOptional = faqService.get(id);
        if (faqOptional.isPresent()) {
            FaqResource faqResource = modelMapper.map(
                    faqOptional.get(), FaqResource.class);
            modelMapper.map(patchValues, faqResource);
            faqService.update(id, modelMapper.map(faqResource, Faq.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing faq.
     *
     * @param id id of the faq to delete
     * @return response with http status 204 or 404 if the faq wasn't found
     */
    @DeleteMapping("faqs/{id}")
    public ResponseEntity deleteFaq(@PathVariable("id") Long id) {
        if (faqService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
