package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends ReadOperationRepository<User> {
    Page<User> findAll(Pageable pageable);
}
