package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;


public interface UserRepository extends ReadOperationRepository<User> {
    Page<User> findAll(Pageable pageable);
}
