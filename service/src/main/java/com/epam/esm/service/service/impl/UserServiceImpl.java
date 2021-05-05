package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.PageImpl;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND = "user with id = %s not found";
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserInfoDto getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND, id)));
        return mapper.map(user, UserInfoDto.class);
    }

    @Override
    public Page<UserInfoDto> getAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<UserInfoDto> contentDto = page.getContent().stream()
                .map(order -> mapper.map(order, UserInfoDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(contentDto, pageable, page.getLastPage());
    }
}
