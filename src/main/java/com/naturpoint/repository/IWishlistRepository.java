package com.naturpoint.repository;

import com.naturpoint.model.Wishlist;

import java.util.List;


public interface IWishlistRepository extends IGenericRepo<Wishlist, Integer> {

    List<Wishlist> findAllByUser_UsernameOrderByCreatedDateDesc(String username);
}
