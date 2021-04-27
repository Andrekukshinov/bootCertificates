package com.epam.esm.service.service;

import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;

import java.util.Set;

public interface UserService {
    UserOrderDto getById(Long id);

    Set<UserInfoDto> getAll();
}
