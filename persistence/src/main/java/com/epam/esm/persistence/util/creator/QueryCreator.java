package com.epam.esm.persistence.util.creator;

import com.epam.esm.persistence.model.SearchSpecification;
import com.epam.esm.persistence.model.SortSpecification;
import com.epam.esm.persistence.model.enums.SortDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class QueryCreator {

    private static final String UPDATE = "UPDATE ";

    private static final String SEARCH_CERTIFICATE_BY_TAG_NAME =
            " id IN (\n" +
                    "           SELECT tgc.gift_certificate_id FROM tags_gift_certificates AS tgc\n" +
                    "            INNER JOIN tags AS t ON tgc.tag_id = t.id \n" +
                    "            WHERE t.name LIKE ?\n" +
                    ") ";

    private static final String FIND_CERTIFICATE_BY_CONDITION =
            " SELECT id, name, description, duration, create_date, last_update_date, price " +
                    " FROM gift_certificates " +
                    " WHERE TRUE %s " +
                    " ORDER BY NULL %s ";


    public String getSelectSpecificationQuery(SearchSpecification searchSpecification, SortSpecification sortSpecification) {
        String query = FIND_CERTIFICATE_BY_CONDITION;

        String nameCertificateSearch = searchSpecification.getCertificateName();
        String descriptionCertificateSearch = searchSpecification.getCertificateDescription();
        String tagSearch = searchSpecification.getTagName();
        String searchCondition = getSearchCondition(nameCertificateSearch, descriptionCertificateSearch, tagSearch);

        SortDirection createDateSortConditionDir = sortSpecification.getCreateDateSortDir();
        SortDirection nameSortConditionDir = sortSpecification.getNameSortDir();
        String sort = getSortCondition(createDateSortConditionDir, nameSortConditionDir);

        return String.format(query, searchCondition, sort);
    }

    public String getUpdateQuery(String tableName, Set<String> fieldNames, String updateParam) {
        StringBuilder query = new StringBuilder(UPDATE);
        query.append(tableName);
        query.append(" SET ");
        String setValuesPart = getJoinedString("=?, ", fieldNames);
        query.append(setValuesPart).append("=?").append(" WHERE ").append(updateParam).append("=?");
        return query.toString();
    }

    private String getSortCondition(SortDirection createDateSortConditionDir, SortDirection nameSortConditionDir) {
        String sort = StringUtils.EMPTY;
        if (createDateSortConditionDir != null) {
            sort = getPartSortCondition(createDateSortConditionDir, " ,create_date ");
        }
        if (nameSortConditionDir != null) {
            sort = getPartSortCondition(nameSortConditionDir, " ,name ");
        }
        return sort;
    }

    private String getPartSortCondition(SortDirection sortConditionDir, String sortCondition) {
        return sortCondition + sortConditionDir;
    }

    private String getSearchCondition(String nameCertificateSearch, String descriptionCertificateSearch, String tagSearch) {
        StringBuilder result = new StringBuilder();
        if (nameCertificateSearch != null) {
            result.append(" AND name LIKE CONCAT('%', ?, '%')");
        }
        if (descriptionCertificateSearch != null) {
            result.append(" AND description LIKE CONCAT('%', ?, '%')");
        }
        if (tagSearch != null) {
            result.append(" AND ").append(SEARCH_CERTIFICATE_BY_TAG_NAME);
        }
        return result.toString();
    }

    private String getJoinedString(String joinWith, Collection<? extends String> forJoining) {
        return String.join(joinWith, forJoining);
    }
}
