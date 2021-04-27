package com.epam.esm.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_certificates")
public class OrderCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private GiftCertificate orderCertificate;
    private Integer quantity;

    public OrderCertificate() {
    }

    public OrderCertificate(Long id, Order order, GiftCertificate orderCertificate, Integer quantity) {
        this.id = id;
        this.order = order;
        this.orderCertificate = orderCertificate;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GiftCertificate getOrderCertificate() {
        return orderCertificate;
    }

    public void setOrderCertificate(GiftCertificate orderCertificate) {
        this.orderCertificate = orderCertificate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


//    
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        OrderCertificate that = (OrderCertificate) o;
//        return Objects.equals(getId(), that.getId()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getOrderCertificate(), that.getOrderCertificate());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getOrder(), getOrderCertificate());
//    }
}
