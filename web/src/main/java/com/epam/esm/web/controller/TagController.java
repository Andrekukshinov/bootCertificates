package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.valiation.SaveGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final PagedResourcesAssembler<TagDto> assembler;

    @Autowired
    public TagController(TagService tagService, PagedResourcesAssembler<TagDto> assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<TagDto> saveTag(@Validated(SaveGroup.class) @RequestBody TagDto tag) throws ValidationException {
        TagDto saved = tagService.save(tag);
        saved.add(linkTo(methodOn(TagController.class).getTagById(saved.getId())).withRel("this_tag"));
        getAllRef(saved);
        return ResponseEntity.ok(saved);

    }

    private void getAllRef(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).getAll(Pageable.unpaged())).withRel("all"));
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
    public ResponseEntity<PagedModel<EntityModel<TagDto>>> getAll(Pageable pageable) {
        Page<TagDto> all = tagService.getAll(pageable);
        PagedModel<EntityModel<TagDto>> entityModels = assembler.toModel(all);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/top/user/popular/tag")
    public ResponseEntity<TagDto> getTopUserMostPopularTag () {
        TagDto tag = tagService.getTopUserMostPopularTag();
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("this_tag"));
        tag.add(linkTo(methodOn(TagController.class).getTopUserMostPopularTag()).withSelfRel());
        getAllRef(tag);
        return ResponseEntity.ok(tag);
    }

}
