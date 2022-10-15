package com.naturpoint.service.impl;

import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.service.ICRUD;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepository();

    @Override
    public T save(T t) {
        return getRepository().save(t);
    }

    @Override
    public T update(T t) {
        return getRepository().save(t);
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }
}
