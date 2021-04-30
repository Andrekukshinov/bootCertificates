//package com.epam.esm.service.validation.impl;
//
//import com.epam.esm.persistence.entity.Tag;
//import com.epam.esm.service.dto.TagDto;
//import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
//import com.epam.esm.service.exception.ValidationException;
//import com.epam.esm.service.validation.SaveValidator;
//import com.epam.esm.service.validation.Validator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class GiftCertificateTagDtoUpdateValidatorTest {
//    private static final LocalDateTime DATE = LocalDateTime.parse("2021-03-25T00:00:00");
//    private static final String SPA = "SPA";
//    private static final String FAMILY_CERTIFICATE = "FAMILY_CERTIFICATE";
//
//    @Mock
//    private Validator<Tag> tagDtoUpdateValidator;
//
//    @InjectMocks
//    private GiftCertificateTagDtoUpdateValidator validator;
//
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    private static Stream<Arguments> dataProvider() {
//        GiftCertificateTagDto createDateSpecified = GiftCertificateTagDto.getBuilder()
//                .setId(11L)
//                .setCreateDate(DATE)
//                .setLastUpdateDate(DATE)
//                .setName(SPA)
//                .setDescription(FAMILY_CERTIFICATE)
//                .setPrice(new BigDecimal(754))
//                .setDuration(3)
//                .build();
//        GiftCertificateTagDto updateDateDateSpecified = GiftCertificateTagDto.getBuilder()
//                .setId(-1L)
//                .setLastUpdateDate(DATE)
//                .setName(SPA)
//                .setDescription(FAMILY_CERTIFICATE)
//                .setPrice(new BigDecimal(754))
//                .setDuration(3)
//                .build();
//        String tooLongName = "123456789012345678901234567890123456789012345678901";
//        GiftCertificateTagDto longName = GiftCertificateTagDto.getBuilder()
//                .setId(-1L)
//                .setName(tooLongName)
//                .setDescription(FAMILY_CERTIFICATE)
//                .setPrice(new BigDecimal(754))
//                .setDuration(3)
//                .build();
//        String tooLongDescription = "1123456789012345678901234567890123456789012345678901123456789012" +
//                "3456789012345678901234567890123456789011234567890123456789012345678901234567890123456" +
//                "7890112345678901234567890123456789012345678901234567890112345678901234567890123456789" +
//                "0123456789012345678901";
//        GiftCertificateTagDto longDescr = GiftCertificateTagDto.getBuilder()
//                .setId(-1L)
//                .setName(SPA)
//                .setDescription(tooLongDescription)
//                .setPrice(new BigDecimal(754))
//                .setDuration(3)
//                .build();
//        GiftCertificateTagDto invalidPrice = GiftCertificateTagDto.getBuilder()
//                .setId(-1L)
//                .setName(tooLongName)
//                .setDescription(FAMILY_CERTIFICATE)
//                .setPrice(new BigDecimal(-754))
//                .setDuration(3)
//                .build();
//        GiftCertificateTagDto invalidDuration = GiftCertificateTagDto.getBuilder()
//                .setId(-1L)
//                .setName(tooLongName)
//                .setDescription(FAMILY_CERTIFICATE)
//                .setPrice(new BigDecimal(754))
//                .setDuration(3)
//                .build();
//
//        return Stream.of(
//                Arguments.of(longName, "name cannot be longer than 50 symbols!"),
//                Arguments.of(longDescr, "description cannot be longer than 255 symbols!"),
//                Arguments.of(createDateSpecified, "create date cannot be specified!"),
//                Arguments.of(updateDateDateSpecified, "last update date cannot be specified!"),
//                Arguments.of(invalidDuration, "duration cannot be negative!"),
//                Arguments.of(invalidPrice, "price cannot be negative!")
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("dataProvider")
//    void testValidateShouldThrowExceptionWhenInvalidObject(GiftCertificateTagDto dto, String msg) {
//        assertThrows(ValidationException.class, () -> validator.validate(dto), msg);
//    }
//}
