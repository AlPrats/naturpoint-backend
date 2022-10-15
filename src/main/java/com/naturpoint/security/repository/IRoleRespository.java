package com.naturpoint.security.repository;

import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.security.enums.RoleName;
import com.naturpoint.security.model.Role;

import java.util.Optional;

public interface IRoleRespository extends IGenericRepo<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
