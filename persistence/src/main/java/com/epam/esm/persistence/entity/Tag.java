package com.epam.esm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(getId(), tag.getId()) && Objects.equals(getName(), tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}


// // todo delete this
//    @ManyToMany
//    @JoinTable(
//            name = "tags_gift_certificates",
//            joinColumns = {
//                    @JoinColumn(name = "tag_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "gift_certificate_id")
//            }
//    )
//    private Set<GiftCertificate> certificates;
//
//    public Tag(Long id, String name, Set<GiftCertificate> certificates) {
//        this.id = id;
//        this.name = name;
//        this.certificates = certificates;
//    }
//
//    public Tag() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public Set<GiftCertificate> getCertificates() {
//        return certificates;
//    }
//
//    public void setCertificates(Set<GiftCertificate> certificates) {
//        this.certificates = certificates;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Tag tag = (Tag) o;
//        return Objects.equals(getId(), tag.getId()) && Objects.equals(getName(), tag.getName()) && Objects.equals(getCertificates(), tag.getCertificates());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getName(), getCertificates());
//    }
