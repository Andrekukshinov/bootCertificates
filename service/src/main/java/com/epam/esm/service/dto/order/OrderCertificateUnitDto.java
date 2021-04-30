package com.epam.esm.service.dto.order;

import com.epam.esm.service.dto.certificate.GiftCertificatesNoTagDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class OrderCertificateUnitDto {
    @Null(message = "id must be null")
    private Long id;
    @NotNull(message = "certificate must not be null")
    @Valid
    private GiftCertificatesNoTagDto orderCertificate;
    @Min(value = 1, message = "certificate amount cannot be less than 1!")
    @NotNull(message = "certificate quantity must not be null")
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
