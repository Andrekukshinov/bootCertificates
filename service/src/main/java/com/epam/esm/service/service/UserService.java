package com.epam.esm.service.service;

import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserOrderDto getById(Long id);

    Page<UserInfoDto> getAll(Pageable pageable);
}
