package com.naturpoint.service;

import com.naturpoint.dto.cart.AddToCartDTO;
import com.naturpoint.dto.cart.CartDTO;
import com.naturpoint.model.Cart;
import com.naturpoint.security.model.User;

public interface ICartService extends ICRUD<Cart, Integer> {

    Cart addToCart(AddToCartDTO addToCartDTO, User user);

    CartDTO listCartItems(User user);

    void deleteCartItem(Integer idCartItem, User user);
}
