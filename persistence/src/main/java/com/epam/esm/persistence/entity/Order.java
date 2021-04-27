package com.epam.esm.persistence.entity;


import com.epam.esm.persistence.entity.enums.OrderStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderCertificate> orderCertificates;

    public Order(Long id, LocalDateTime createDate, OrderStatus status, BigDecimal totalPrice, User user, List<OrderCertificate> orderCertificates) {
        this.id = id;
        this.createDate = createDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.user = user;
        this.orderCertificates = orderCertificates;
    }

    public Order() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderCertificate> getOrderCertificates() {
        return orderCertificates;
    }

    public void setOrderCertificates(List<OrderCertificate> orderCertificates) {
        this.orderCertificates = orderCertificates;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                ", orderCertificates=" + orderCertificates +
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
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(createDate, order.createDate) && status == order.status && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(user, order.user) && Objects.equals(orderCertificates, order.orderCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, status, totalPrice, user, orderCertificates);
    }
}
