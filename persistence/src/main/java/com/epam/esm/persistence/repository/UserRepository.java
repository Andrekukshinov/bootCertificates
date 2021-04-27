package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.User;

import java.util.Set;

public interface UserRepository extends ReadOperationRepository<User> {
    Set<User> findAll();
}
