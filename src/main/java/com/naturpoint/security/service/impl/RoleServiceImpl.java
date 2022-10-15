package com.naturpoint.security.service.impl;

import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.security.enums.RoleName;
import com.naturpoint.security.model.Role;
import com.naturpoint.security.repository.IRoleRespository;
import com.naturpoint.security.service.IRoleService;
import com.naturpoint.service.impl.CRUDImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    @Autowired
    IRoleRespository repository;

    @Override
    protected IGenericRepo<Role, Integer> getRepository() {
        return repository;
    }

    @Override
    public Optional<Role> getByRoleName(RoleName roleName) {
        return repository.findByRoleName(roleName);
    }
}
