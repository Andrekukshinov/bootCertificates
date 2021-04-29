package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<TagDto> saveTag(@RequestBody TagDto tag) throws ValidationException {
        TagDto saved = tagService.save(tag);
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("tag_link"));
        tag.add(linkTo(methodOn(TagController.class).getAll(null)).withRel("all"));
        return ResponseEntity.ok(saved);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        TagDto tag = tagService.getTag(id);
        tag.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).getAll(null)).withRel("all"));
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RepresentationModel<?>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        RepresentationModel<?> representationModel = new RepresentationModel<>();
        representationModel.add(linkTo(methodOn(TagController.class).getAll(null)).withRel("all"));
        return ResponseEntity.ok(representationModel);
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
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("get by id"));
        tag.add(linkTo(methodOn(TagController.class).getTopUserMostPopularTag()).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).getAll(null)).withRel("all"));
        return ResponseEntity.ok(tag);
    }

}
