package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.extractor.impl.TagGiftCertificateIdFieldsExtractor;
import com.epam.esm.persistence.mapper.GiftCertificateRowMapper;
import com.epam.esm.persistence.mapper.TagRowMapper;
import com.epam.esm.persistence.model.TagGiftCertificateId;
import com.epam.esm.persistence.repository.TagGiftCertificateRepository;
import com.epam.esm.persistence.util.jdbc.TagGiftCertificateSimpleJdbcInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class TagGiftCertificateRepositoryImpl implements TagGiftCertificateRepository {

    private static final String DELETE_CERTIFICATE_TAGS = " DELETE FROM tags_gift_certificates" +
            " WHERE tags_gift_certificates.gift_certificate_id = ?";
    private static final String GET_CERTIFICATE_TAGS =
            " SELECT T.id as id, name " +
                    " FROM tags AS T" +
                    " INNER JOIN tags_gift_certificates AS TGC ON T.id = TGC.tag_id" +
                    " WHERE TGC.gift_certificate_id =?";
    private static final String DELETE_TAGS = " DELETE FROM tags_gift_certificates" +
            " WHERE tags_gift_certificates.tag_id = ?";

    private final JdbcTemplate jdbc;
    private final TagRowMapper tagRowMapper;
    private final GiftCertificateRowMapper certificateRowMapper;
    private final SimpleJdbcInsert jdbcInsert;
    private final TagGiftCertificateIdFieldsExtractor extractor;

    @Autowired
    public TagGiftCertificateRepositoryImpl(JdbcTemplate jdbc, TagRowMapper tagRowMapper,
                                            GiftCertificateRowMapper certificateRowMapper,
                                            TagGiftCertificateSimpleJdbcInsert jdbcInsert,
                                            TagGiftCertificateIdFieldsExtractor extractor) {
        this.jdbc = jdbc;
        this.tagRowMapper = tagRowMapper;
        this.certificateRowMapper = certificateRowMapper;
        this.jdbcInsert = jdbcInsert;
        this.extractor = extractor;
    }

    @Override
    public void deleteCertificateTags(Long id) {
        jdbc.update(DELETE_CERTIFICATE_TAGS, id);
    }

    @Override
    @Transactional
    public void saveGiftCertificateTags(List<TagGiftCertificateId> tagGiftCertificateIds) {
        tagGiftCertificateIds.forEach(tagGiftCertificateId -> {
            Map<String, Object> fieldsValuesMap = extractor.getFieldsValuesMap(tagGiftCertificateId);
            jdbcInsert.execute(fieldsValuesMap);
        });
    }

    @Override
    public Set<Tag> findCertificateTags(Long certificateId) {
        return new HashSet<>(jdbc.query(GET_CERTIFICATE_TAGS, tagRowMapper, certificateId));
    }

    @Override
    public void deleteTagFromCertificates(Long tagId) {
        jdbc.update(DELETE_TAGS, tagId);
    }

}
