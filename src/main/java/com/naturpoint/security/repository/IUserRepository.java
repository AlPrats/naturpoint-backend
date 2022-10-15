package com.naturpoint.security.repository;

import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.security.model.User;

import java.util.Optional;

public interface IUserRepository extends IGenericRepo<User, Integer> {

    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
}
