package com.naturpoint.security.service;

import com.naturpoint.security.model.User;
import com.naturpoint.service.ICRUD;

import java.util.Optional;

public interface IUserService extends ICRUD<User, Integer> {

    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
}
