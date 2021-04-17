package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateTagDto;
import com.epam.esm.service.dto.GiftCertificatesNoTagDto;
import com.epam.esm.service.dto.SpecificationDto;
import com.epam.esm.service.exception.ValidationException;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveGiftCertificate(@RequestBody GiftCertificateTagDto certificate) throws ValidationException {

        certificateService.save(certificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable(ID) Long id) {
        certificateService.deleteCertificate(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCertificate(@RequestBody GiftCertificateTagDto certificateDto, @PathVariable Long id) throws ValidationException {
        certificateService.updateCertificate(certificateDto, id);
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificatesNoTagDto>> getByParam(SpecificationDto specification) {
        List<GiftCertificatesNoTagDto> bySpecification = certificateService.getBySpecification(specification);
        return ResponseEntity.ok(bySpecification);
    }
}
