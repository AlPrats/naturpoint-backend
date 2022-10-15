package com.naturpoint.service;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.model.Wishlist;

import java.util.List;


public interface IWishlistService extends ICRUD<Wishlist, Integer> {

    List<ProductDTO> getWishlist(String username);

}
