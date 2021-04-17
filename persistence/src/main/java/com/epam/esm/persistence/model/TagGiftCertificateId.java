package com.epam.esm.persistence.model;

public class TagGiftCertificateId {
    private final Long certificateId;
    private final Long tagId;

    public TagGiftCertificateId(Long certificateId, Long tagId) {
        this.certificateId = certificateId;
        this.tagId = tagId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public Long getTagId() {
        return tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagGiftCertificateId that = (TagGiftCertificateId) o;

        if (getCertificateId() != null ? !getCertificateId().equals(that.getCertificateId()) : that.getCertificateId() != null) {
            return false;
        }
        return getTagId() != null ? getTagId().equals(that.getTagId()) : that.getTagId() == null;
    }

    @Override
    public int hashCode() {
        int result = getCertificateId() != null ? getCertificateId().hashCode() : 0;
        result = 31 * result + (getTagId() != null ? getTagId().hashCode() : 0);
        return result;
    }
}
