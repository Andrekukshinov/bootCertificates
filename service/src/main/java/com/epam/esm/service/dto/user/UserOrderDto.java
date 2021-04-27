package com.epam.esm.service.dto.user;

import com.epam.esm.service.dto.order.OrderNoCertificatesDto;

import java.util.Objects;
import java.util.Set;

public class UserOrderDto {
    private Long id;
    private String password;
    private String email;
    private String nickname;
    private Set<OrderNoCertificatesDto> orders;

    public UserOrderDto() {
    }

    public UserOrderDto(Long id, String password, String email, String nickname, Set<OrderNoCertificatesDto> orders) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public Set<OrderNoCertificatesDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderNoCertificatesDto> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserOrderDto userDto = (UserOrderDto) o;
        return Objects.equals(getId(), userDto.getId()) && Objects.equals(getPassword(), userDto.getPassword()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getNickname(), userDto.getNickname()) && Objects.equals(getOrders(), userDto.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword(), getEmail(), getNickname(), getOrders());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", orders=" + orders +
                '}';
    }
}
