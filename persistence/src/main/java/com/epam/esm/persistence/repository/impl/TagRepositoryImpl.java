package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.extractor.FieldsExtractor;
import com.epam.esm.persistence.mapper.TagRowMapper;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.util.jdbc.TagSimpleJdbcInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String GET_BY_ID = "SELECT id, name FROM tags WHERE id = ?";
    private static final String GET_ALL = "SELECT id, name FROM tags ";
    private static final String GET_BY_NAME = "SELECT id, name FROM tags WHERE name = ?";
    private static final String DELETE_TAG = "DELETE FROM tags WHERE id = ?";
    private static final String COMMA = ", ";
    private static final String GET_ALL_WHERE_NAME_IN = "SELECT id, name FROM tags WHERE name IN (%s)";

    private final JdbcTemplate jdbc;
    private final TagRowMapper mapper;
    private final FieldsExtractor<Tag> tagFieldsExtractor;
    private final SimpleJdbcInsert jdbcInsert;


    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbc, TagRowMapper mapper,
                             FieldsExtractor<Tag> tagFieldsExtractor,
                             TagSimpleJdbcInsert jdbcInsert) {
        this.jdbc = jdbc;
        this.mapper = mapper;
        this.tagFieldsExtractor = tagFieldsExtractor;
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public Long save(Tag tag) {
        Map<String, Object> fieldsValuesMap = tagFieldsExtractor.getFieldsValuesMap(tag);
        Number number = jdbcInsert.executeAndReturnKey(fieldsValuesMap);
        return number.longValue();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbc.query(GET_BY_ID, mapper, id)));
    }

    @Override
    public int delete(Long id) {
        return jdbc.update(DELETE_TAG, id);
    }

    @Override
    public Set<Tag> findTagsByNames(Set<String> tagNames) {
        String sqlNamesPlaceHolder = String.join(COMMA, Collections.nCopies(tagNames.size(), "?"));
        String query = String.format(GET_ALL_WHERE_NAME_IN, sqlNamesPlaceHolder);
        return Set.copyOf(jdbc.query(query, mapper, tagNames.toArray()));
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbc.query(GET_BY_NAME, mapper, tagName)));
    }

    @Override
    public Set<Tag> findAll() {
        return Set.copyOf(jdbc.query(GET_ALL, mapper));
    }
}
