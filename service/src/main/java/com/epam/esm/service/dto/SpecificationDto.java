package com.epam.esm.service.dto;

import com.epam.esm.persistence.model.enums.SortDirection;

public class SpecificationDto {
    private String tagName;
    private String certificateDescription;
    private String certificateName;
    private SortDirection nameSortDir;
    private SortDirection createDateSortDir;

    public SpecificationDto(SortDirection createDateSortDir, SortDirection nameSortDir,
                            String tagName, String certificateDescription, String certificateName) {
        this.createDateSortDir = createDateSortDir;
        this.nameSortDir = nameSortDir;
        this.tagName = tagName;
        this.certificateDescription = certificateDescription;
        this.certificateName = certificateName;
    }

    public SpecificationDto() {
    }

    public SortDirection getCreateDateSortDir() {
        return createDateSortDir;
    }

    public void setCreateDateSortDir(SortDirection createDateSortDir) {
        this.createDateSortDir = createDateSortDir;
    }

    public SortDirection getNameSortDir() {
        return nameSortDir;
    }

    public void setNameSortDir(SortDirection nameSortDir) {
        this.nameSortDir = nameSortDir;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    @Override
    public String toString() {
        return "SpecificationDto{" +
                "createDateSortDir=" + createDateSortDir +
                ", nameSortDir=" + nameSortDir +
                ", tagName='" + tagName + '\'' +
                ", certificateDescription='" + certificateDescription + '\'' +
                ", certificateName='" + certificateName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecificationDto that = (SpecificationDto) o;

        if (getCreateDateSortDir() != that.getCreateDateSortDir()) {
            return false;
        }
        if (getNameSortDir() != that.getNameSortDir()) {
            return false;
        }
        if (getTagName() != null ? !getTagName().equals(that.getTagName()) : that.getTagName() != null) {
            return false;
        }
        if (getCertificateDescription() != null ? !getCertificateDescription().equals(that.getCertificateDescription()) : that.getCertificateDescription() != null) {
            return false;
        }
        return getCertificateName() != null ? getCertificateName().equals(that.getCertificateName()) : that.getCertificateName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCreateDateSortDir() != null ? getCreateDateSortDir().hashCode() : 0;
        result = 31 * result + (getNameSortDir() != null ? getNameSortDir().hashCode() : 0);
        result = 31 * result + (getTagName() != null ? getTagName().hashCode() : 0);
        result = 31 * result + (getCertificateDescription() != null ? getCertificateDescription().hashCode() : 0);
        result = 31 * result + (getCertificateName() != null ? getCertificateName().hashCode() : 0);
        return result;
    }
}
