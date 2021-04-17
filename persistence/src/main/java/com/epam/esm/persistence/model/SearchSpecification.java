package com.epam.esm.persistence.model;

public class SearchSpecification {
    private String tagName;
    private String certificateDescription;
    private String certificateName;

    public SearchSpecification(String tagName, String certificateDescription, String certificateName) {
        this.tagName = tagName;
        this.certificateDescription = certificateDescription;
        this.certificateName = certificateName;
    }

    public SearchSpecification() {
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
        return "SearchSpecification{" +
                "tagNameSearch='" + tagName + '\'' +
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

        SearchSpecification that = (SearchSpecification) o;

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
        int result = getTagName() != null ? getTagName().hashCode() : 0;
        result = 31 * result + (getCertificateDescription() != null ? getCertificateDescription().hashCode() : 0);
        result = 31 * result + (getCertificateName() != null ? getCertificateName().hashCode() : 0);
        return result;
    }
}
