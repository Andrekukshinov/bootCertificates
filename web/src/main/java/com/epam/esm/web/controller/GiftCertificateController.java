package com.epam.esm.web.controller;

import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.model.RequestParams;
import com.epam.esm.service.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {
    private static final String ID = "id";

    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateTagDto> getGiftCertificateById(@PathVariable(ID) Long id) {
        return ResponseEntity.ok(certificateService.getCertificateWithTagsById(id));
    }

    @PostMapping
    public ResponseEntity<GiftCertificateTagDto> saveGiftCertificate(@RequestBody GiftCertificateTagDto certificate) throws ValidationException {
        GiftCertificateTagDto saved = certificateService.save(certificate);
        return ResponseEntity.ok(saved);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable(ID) Long id) {
        certificateService.deleteCertificate(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateTagDto> updateCertificate(@RequestBody GiftCertificateTagDto certificateDto, @PathVariable Long id) throws ValidationException {
        GiftCertificateTagDto updated = certificateService.updateCertificate(certificateDto, id);
        return ResponseEntity.ok(updated);

    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateTagDto>> getByParam(RequestParams specification) {
        List<GiftCertificateTagDto> bySpecification = certificateService.getBySpecification(specification);
        return ResponseEntity.ok(bySpecification);
    }

}
