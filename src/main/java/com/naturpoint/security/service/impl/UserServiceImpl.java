package com.naturpoint.security.service.impl;

import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.security.model.User;
import com.naturpoint.security.repository.IUserRepository;
import com.naturpoint.security.service.IUserService;
import com.naturpoint.service.impl.CRUDImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Override
    protected IGenericRepo<User, Integer> getRepository() {
        return repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Boolean existsUserByUsername(String username) {
        return repository.existsUserByUsername(username);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return repository.existsUserByEmail(email);
    }
}
