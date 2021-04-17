package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.extractor.FieldsExtractor;
import com.epam.esm.persistence.extractor.SearchParametersExtractor;
import com.epam.esm.persistence.model.SearchSpecification;
import com.epam.esm.persistence.model.SortSpecification;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.util.creator.QueryCreator;
import com.epam.esm.persistence.util.jdbc.GiftCertificateSimpleJdbcInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String TABLE_NAME = "gift_certificates";
    private static final String WHERE_ID = " WHERE id = ?";
    private static final String GET_BY_ID = "SELECT id, name, description, duration, create_date, last_update_date, price FROM " + TABLE_NAME + WHERE_ID;
    private static final String DELETE_CERTIFICATE = " DELETE FROM gift_certificates WHERE id = ?";
    private static final String ID = "id";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;
    private final FieldsExtractor<GiftCertificate> certificateExtractor;
    private final QueryCreator queryCreator;
    private final SearchParametersExtractor searchParametersExtractor;
    private final RowMapper<GiftCertificate> mapper;

    @Autowired
    public GiftCertificateRepositoryImpl(
            JdbcTemplate jdbc,
            GiftCertificateSimpleJdbcInsert jdbcInsert,
            FieldsExtractor<GiftCertificate> certificateExtractor,
            QueryCreator queryCreator, RowMapper<GiftCertificate> mapper,
            SearchParametersExtractor searchParametersExtractor) {
        this.jdbc = jdbc;
        this.jdbcInsert = jdbcInsert;
        this.certificateExtractor = certificateExtractor;
        this.queryCreator = queryCreator;
        this.mapper = mapper;
        this.searchParametersExtractor = searchParametersExtractor;
    }

    @Override
    public Long save(GiftCertificate certificate) {
        Map<String, Object> fieldsValuesMap = certificateExtractor.getFieldsValuesMap(certificate);
        Number number = jdbcInsert.executeAndReturnKey(fieldsValuesMap);
        return number.longValue();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbc.query(GET_BY_ID, mapper, id)));
    }

    @Override
    public int delete(Long id) {
        return jdbc.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public int update(GiftCertificate certificate) {
        Map<String, Object> fieldsValuesMap = certificateExtractor.getFieldsValuesMap(certificate);
        Object updateParam = fieldsValuesMap.get(ID);
        String updateQuery = queryCreator.getUpdateQuery(TABLE_NAME, fieldsValuesMap.keySet(), ID);
        List<Object> objects = new ArrayList<>(fieldsValuesMap.values());
        objects.add(updateParam);
        return jdbc.update(updateQuery, objects.toArray());
    }

    @Override
    public List<GiftCertificate> findBySpecification(SearchSpecification searchSpecification, SortSpecification sortSpecification) {
        String qry = queryCreator.getSelectSpecificationQuery(searchSpecification, sortSpecification);
        List<Object> values = searchParametersExtractor.getValues(searchSpecification);
        return jdbc.query(qry, mapper, values.toArray());
    }

}
