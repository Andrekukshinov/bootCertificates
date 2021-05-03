package com.epam.esm.service.service;

import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;


public interface UserService {
    UserOrderDto getById(Long id);

    Page<UserInfoDto> getAll(Pageable pageable);
}
