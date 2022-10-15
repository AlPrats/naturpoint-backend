package com.naturpoint.security.service;

import com.naturpoint.security.enums.RoleName;
import com.naturpoint.security.model.Role;
import com.naturpoint.service.ICRUD;

import java.util.Optional;

public interface IRoleService extends ICRUD<Role, Integer> {

    Optional<Role> getByRoleName(RoleName roleName);
}
