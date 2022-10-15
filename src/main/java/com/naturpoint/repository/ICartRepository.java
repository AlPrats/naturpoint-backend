package com.naturpoint.repository;

import com.naturpoint.model.Cart;
import com.naturpoint.security.model.User;

import java.util.Set;

public interface ICartRepository extends IGenericRepo<Cart, Integer> {

    Set<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
