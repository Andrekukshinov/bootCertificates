package com.epam.esm.service.service;

import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.service.dto.user.UserInfoDto;


public interface UserService {
    UserInfoDto getById(Long id);

    Page<UserInfoDto> getAll(Pageable pageable);
}
