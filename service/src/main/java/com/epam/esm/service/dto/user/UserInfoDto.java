package com.epam.esm.service.dto.user;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserInfoDto extends RepresentationModel<UserOrderDto> {
    private Long id;
    private String password;
    private String email;
    private String nickname;

    public UserInfoDto() {
    }

    public UserInfoDto(Long id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
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

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
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
        UserInfoDto that = (UserInfoDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getNickname(), that.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword(), getEmail(), getNickname());
    }
}
