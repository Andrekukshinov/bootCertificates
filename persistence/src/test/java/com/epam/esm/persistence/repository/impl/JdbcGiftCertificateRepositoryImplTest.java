package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.config.TestConfiguration;
import com.epam.esm.persistence.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("test")
@Transactional
class JdbcGiftCertificateRepositoryImplTest {
    private static final Long EXPECTED_SAVED_ID = 5L;
    private static final int EXPECTED_AMOUNT = 1;
    private static final LocalDateTime DATE = LocalDateTime.parse("2021-03-25T00:00:00");
    private static final String SPA = "spa";
    private static final String FAMILY_CERTIFICATE = "family certificate";
    private static final String GYM = "gym";
    private static final String GYM_CERTIFICATE = "for boss of the gym";
    private static final String POOL = "pool";
    private static final String CONNECTION_POOL = "for better connection";
    private static final String CLUP = "clup";
    private static final String CLUP_GIFT = "for leatherman";

    private static final GiftCertificate FOR_SAVING = GiftCertificate.getBuilder()
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .build();

    private static final GiftCertificate FIRST = GiftCertificate.getBuilder()
            .setId(1L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .build();

    private static final GiftCertificate SECOND = GiftCertificate.getBuilder()
            .setId(2L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(GYM)
            .setDescription(GYM_CERTIFICATE)
            .setPrice(new BigDecimal(300))
            .setDuration(14)
            .build();

    private static final GiftCertificate THIRD = GiftCertificate.getBuilder()
            .setId(3L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(POOL)
            .setDescription(CONNECTION_POOL)
            .setPrice(new BigDecimal(354))
            .setDuration(23)
            .build();

    private static final GiftCertificate FOURTH = GiftCertificate.getBuilder()
            .setId(4L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(CLUP)
            .setDescription(CLUP_GIFT)
            .setPrice(new BigDecimal(150))
            .setDuration(9)
            .build();

    private static final GiftCertificate FOR_UPDATING = GiftCertificate.getBuilder()
            .setId(3L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(755))
            .setDuration(3)
            .build();

    //todo fixme
//    private static Stream<Arguments> dataProvider() {
//        SearchSpecification findFirstAndFourth = new SearchSpecification("people", "e", "p");
//        SearchSpecification findFirstThirdAndFourth = new SearchSpecification(null, "e", "p");
//        SearchSpecification findFirstThirdAndFourthByDescription = new SearchSpecification(null, null, "p");
//        SearchSpecification findAll = new SearchSpecification(null, null, null);
//        SortSpecification nameSortAsc = new SortSpecification(null, SortDirection.ASC);
//        SortSpecification nameSortDesc = new SortSpecification(null, SortDirection.DESC);
//        SortSpecification nameDescriptionSortAsc = new SortSpecification(SortDirection.ASC, SortDirection.ASC);
//        return Stream.of(
//                Arguments.of(findFirstAndFourth, nameSortAsc, List.of(FOURTH, FIRST)),
//                Arguments.of(findFirstThirdAndFourth, nameSortAsc, List.of(FOURTH, THIRD, FIRST)),
//                Arguments.of(findFirstThirdAndFourthByDescription, nameSortDesc, List.of(FIRST, THIRD, FOURTH)),
//                Arguments.of(findAll, nameDescriptionSortAsc, List.of(FOURTH, SECOND, THIRD, FIRST))
//        );
//    }

    @Autowired
    private GiftCertificateRepositoryImpl repository;

    @Test
    @Rollback
    void testFindByIdShouldReturnObjectWhenFound() {
        Optional<GiftCertificate> certificateOptional = repository.findById(1L);

        GiftCertificate giftCertificate = certificateOptional.get();
        assertThat(giftCertificate, is(FIRST));
    }

    @Test
    @Rollback
    void testSaveShouldReturnObjectIdWhenSaved() {
        GiftCertificate saved = repository.save(FOR_SAVING);
//todo doesn't check if types are different
        assertThat(saved, is(FOR_SAVING));
    }

    @Test
    @Rollback
    void testDeleteShouldReturnDeletedObjectsAmountWhenDeleted() {
        int amount = repository.delete(2L);

        assertThat(amount, is(EXPECTED_AMOUNT));
    }

    //fixme
//    @Test
//    @Rollback
//    void testUpdateShouldReturnUpdatedObjectsAmountWhenUpdated() {
//        int amount = repository.update(FOR_UPDATING);
//
//        assertThat(amount, is(EXPECTED_AMOUNT));
//    }


    //todo fixme
//    @ParameterizedTest
//    @MethodSource("dataProvider")
//    @Rollback
//    void testFindBySpecificationShouldReturnListOfObjects(
//            SearchSpecification searchSpecification,
//            SortSpecification sortSpecification,
//            List<GiftCertificate> expectedCertificates) {
//        List<GiftCertificate> giftCertificates = repository.findBySpecification(searchSpecification, sortSpecification);
//
//        assertThat(giftCertificates, is(expectedCertificates));
//    }
}
