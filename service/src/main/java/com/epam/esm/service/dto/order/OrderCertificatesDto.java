package com.epam.esm.service.dto.order;

import com.epam.esm.persistence.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class OrderCertificatesDto {
    private Long id;
    private LocalDateTime createDate;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Long userId;
    private Set<OrderCertificateUnitDto> orderCertificates;

    public OrderCertificatesDto() {
    }

    public OrderCertificatesDto(Long id, LocalDateTime createDate, OrderStatus status, BigDecimal totalPrice, Long userId, Set<OrderCertificateUnitDto> orderCertificates) {
        this.id = id;
        this.createDate = createDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderCertificates = orderCertificates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<OrderCertificateUnitDto> getOrderCertificates() {
        return orderCertificates;
    }

    public void setOrderCertificates(Set<OrderCertificateUnitDto> orderCertificates) {
        this.orderCertificates = orderCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderCertificatesDto orderCertificatesDto = (OrderCertificatesDto) o;
        return Objects.equals(getId(), orderCertificatesDto.getId()) && Objects.equals(getCreateDate(), orderCertificatesDto.getCreateDate()) && getStatus() == orderCertificatesDto.getStatus() && Objects.equals(getTotalPrice(), orderCertificatesDto.getTotalPrice()) && Objects.equals(getUserId(), orderCertificatesDto.getUserId()) && Objects.equals(getOrderCertificates(), orderCertificatesDto.getOrderCertificates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreateDate(), getStatus(), getTotalPrice(), getUserId(), getOrderCertificates());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", orderCertificates=" + orderCertificates +
                '}';
    }
}
