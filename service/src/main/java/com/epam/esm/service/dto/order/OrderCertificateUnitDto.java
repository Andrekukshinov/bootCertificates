package com.epam.esm.service.dto.order;

import com.epam.esm.service.dto.certificate.GiftCertificatesNoTagDto;

public class OrderCertificateUnitDto {
    private Long id;
    private GiftCertificatesNoTagDto orderCertificate;
    private Integer quantity;

    public OrderCertificateUnitDto(Long id, GiftCertificatesNoTagDto orderCertificate, Integer quantity) {
        this.id = id;
        this.orderCertificate = orderCertificate;
//        this.oderId = oderId;
        this.quantity = quantity;
    }

    public OrderCertificateUnitDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftCertificatesNoTagDto getOrderCertificate() {
        return orderCertificate;
    }

    public void setOrderCertificate(GiftCertificatesNoTagDto orderCertificate) {
        this.orderCertificate = orderCertificate;
    }
//
//    public Long getOderId() {
//        return oderId;
//    }
//
//    public void setOderId(Long oderId) {
//        this.oderId = oderId;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
