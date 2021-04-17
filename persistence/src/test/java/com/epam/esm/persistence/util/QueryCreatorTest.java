package com.epam.esm.persistence.util;

import com.epam.esm.persistence.model.SearchSpecification;
import com.epam.esm.persistence.model.SortSpecification;
import com.epam.esm.persistence.model.enums.SortDirection;
import com.epam.esm.persistence.util.creator.QueryCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class QueryCreatorTest {

    private static final QueryCreator queryCreator = new QueryCreator();

    private static Stream<Arguments> dataProvider() {
        SearchSpecification tagCertificateName = new SearchSpecification("true", null, "");
        SortSpecification descrSortAsc = new SortSpecification(SortDirection.DESC, null);
        String tagCertificateQry = " SELECT id, name, description, duration, create_date, last_update_date, price  FROM gift_certificates  WHERE TRUE  AND name LIKE CONCAT('%', ?, '%') AND  id IN (\n" +
                "           SELECT tgc.gift_certificate_id FROM tags_gift_certificates AS tgc\n" +
                "            INNER JOIN tags AS t ON tgc.tag_id = t.id \n" +
                "            WHERE t.name LIKE ?\n" +
                ")   ORDER BY NULL  ,create_date DESC ";

        SearchSpecification certificateName = new SearchSpecification(null, null, "");
        String certificateQry = " SELECT id, name, description, duration, create_date, last_update_date, price  FROM gift_certificates  WHERE TRUE  AND name LIKE CONCAT('%', ?, '%')  ORDER BY NULL  ,create_date DESC ";

        SearchSpecification fullSearch = new SearchSpecification("null", "null", "");
        String completeQry = " SELECT id, name, description, duration, create_date, last_update_date, price  FROM gift_certificates  WHERE TRUE  AND name LIKE CONCAT('%', ?, '%') AND description LIKE CONCAT('%', ?, '%') AND  id IN (\n" +
                "           SELECT tgc.gift_certificate_id FROM tags_gift_certificates AS tgc\n" +
                "            INNER JOIN tags AS t ON tgc.tag_id = t.id \n" +
                "            WHERE t.name LIKE ?\n" +
                ")   ORDER BY NULL  ,create_date DESC ";

        return Stream.of(
                Arguments.of(tagCertificateName, descrSortAsc, tagCertificateQry),
                Arguments.of(certificateName, descrSortAsc, certificateQry),
                Arguments.of(fullSearch, descrSortAsc, completeQry)
        );
    }

    @Test
    void testGetUpdateQueryShouldReturnUpdateQuery() {
        Map<String, Object> values = new LinkedHashMap<>();
        String id = "id";
        values.put("name", 1);
        values.put(id, 1);
        String table = queryCreator.getUpdateQuery("table", values.keySet(), id);
        assertThat(table, is("UPDATE table SET name=?, id=? WHERE id=?"));

    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void testGetFindCertificateByConditionShouldReturnQueryBasedOnSpecification(SearchSpecification search, SortSpecification sort, String expected) {
        String query = queryCreator.getSelectSpecificationQuery(search, sort);
        assertThat(query, is(expected));
    }

}
