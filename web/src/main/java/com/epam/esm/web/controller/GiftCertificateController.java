package com.epam.esm.web.controller;

import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.model.RequestParams;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.valiation.SaveGroup;
import com.epam.esm.service.valiation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {
    private static final String ID = "id";

    private final GiftCertificateService certificateService;
    private final PagedResourcesAssembler<GiftCertificateTagDto> assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService, PagedResourcesAssembler<GiftCertificateTagDto> assembler) {
        this.certificateService = certificateService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateTagDto> getGiftCertificateById(@PathVariable(ID) Long id) {
        GiftCertificateTagDto certificate = certificateService.getCertificateWithTagsById(id);
        certificate.add((linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(id)).withSelfRel()));
        addMappingToAll(certificate);
        return ResponseEntity.ok(certificate);
    }

    private void addMappingToAll(RepresentationModel<?> certificate) {
        certificate.add((linkTo(methodOn(GiftCertificateController.class).getByParam(null, null)).withRel("all")));
    }

    @PostMapping
    public ResponseEntity<GiftCertificateTagDto> saveGiftCertificate(@Validated(SaveGroup.class) @RequestBody GiftCertificateTagDto certificate) throws ValidationException {
        GiftCertificateTagDto saved = certificateService.save(certificate);
        saved.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(saved.getId())).withRel("this"));
        addMappingToAll(saved);
        return ResponseEntity.ok(saved);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable(ID) Long id) {
        certificateService.deleteCertificate(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateTagDto> updateCertificate(@Validated(UpdateGroup.class) @RequestBody GiftCertificateTagDto certificateDto, @PathVariable Long id) throws ValidationException {
        GiftCertificateTagDto updated = certificateService.updateCertificate(certificateDto, id);
        updated.add((linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(id)).withRel("this")));
        addMappingToAll(updated);
        return ResponseEntity.ok(updated);

    }

    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateTagDto>>> getByParam(RequestParams specification, Pageable pageable) {
        Page<GiftCertificateTagDto> bySpecification = certificateService.getBySpecification(specification, pageable);
        PagedModel<EntityModel<GiftCertificateTagDto>> entityModels = assembler.toModel(bySpecification);
        return ResponseEntity.ok(entityModels);
    }

}
