package com.epam.esm.web.controller;

import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.valiation.SaveGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;


    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagDto> saveTag(@Validated(SaveGroup.class) @RequestBody TagDto tag) {
        TagDto saved = tagService.save(tag);
        saved.add(linkTo(methodOn(TagController.class).getTagById(saved.getId())).withRel("this_tag"));
        getAllRef(saved);
        return ResponseEntity.ok(saved);

    }

    private void getAllRef(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).getAll(new HashMap<>())).withRel("all"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        TagDto tag = tagService.getTag(id);
        tag.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        getAllRef(tag);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<TagDto>> getAll(@RequestParam Map<String, String> requestParams) {
        Pageable pageable = getPageable(requestParams);
        Page<TagDto> page = tagService.getAll(pageable);
        CollectionModel<TagDto> of = getTagsBuiltLinks(requestParams, page);
        return ResponseEntity.ok(of);
    }

    private CollectionModel<TagDto> getTagsBuiltLinks(Map<String, String> requestParams, Page<TagDto> page) {
        CollectionModel<TagDto> of = CollectionModel.of(page.getContent());
        of.add(linkTo(
                methodOn(TagController.class)
                        .getAll(getPageParamMap(requestParams, page.getFirstPage())))
                .withRel("first")
        );
        if (page.hasPrevious()) {
            of.add(linkTo(
                    methodOn(TagController.class)
                            .getAll(getPageParamMap(requestParams, page.getPreviousPage())))
                    .withRel("this")
            );
        }
        of.add(linkTo(
                methodOn(TagController.class)
                        .getAll(getPageParamMap(requestParams, page.getPage())))
                .withRel("this")
        );
        if (page.hasNext()) {
            of.add(linkTo(
                    methodOn(TagController.class)
                            .getAll(getPageParamMap(requestParams, page.getNextPage())))
                    .withRel("this")
            );
        }
        of.add(linkTo(
                methodOn(TagController.class)
                        .getAll(getPageParamMap(requestParams, page.getLastPage())))
                .withRel("last")
        );
        return of;
    }

    private Map<String, String> getPageParamMap(Map<String, String> requestParams, Integer page) {
        HashMap<String, String> result = new HashMap<>(requestParams);
        result.put("page", page.toString());
        return result;
    }

    private Pageable getPageable(Map<String, String> requestParams) {
        String pages = requestParams.get("page");
        Integer page = Integer.valueOf(pages);
        Integer size = Integer.valueOf(requestParams.get("size"));
        String sort = requestParams.get("sort");
        String sortDir = requestParams.get("sortDir");
        return new Pageable(page, size, sort, sortDir);
    }

}
