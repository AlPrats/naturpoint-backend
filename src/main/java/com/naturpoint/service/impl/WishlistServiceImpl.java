package com.naturpoint.service.impl;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.model.Wishlist;
import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.repository.IProductRepository;
import com.naturpoint.repository.IWishlistRepository;
import com.naturpoint.service.IProductService;
import com.naturpoint.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl extends CRUDImpl<Wishlist, Integer> implements IWishlistService {

    @Autowired
    private IWishlistRepository repository;

    @Autowired
    private IProductService productService;

    @Override
    protected IGenericRepo<Wishlist, Integer> getRepository() {
        return repository;
    }

    @Override
    public List<ProductDTO> getWishlist(String username) {
        final List<Wishlist> wishlists = repository.findAllByUser_UsernameOrderByCreatedDateDesc(username);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Wishlist wishlist: wishlists) {
            productDTOS.add(productService.getProductDTO(wishlist.getProduct()));
        }
        return productDTOS;
    }



    /*@Override
    public List<ProductDTO> getWishlistByUser(String username) {
        List<Wishlist> wishlists = repository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDTO> products = new ArrayList<>();
        for (Wishlist wishlist: wishlists) {
            products.add(productRepository.getProductDTO(wishlist.getProduct()));
        }
        return products;
        //return repository.findAllByUserOrderByCreatedDateDesc(user);
    }*/
}
