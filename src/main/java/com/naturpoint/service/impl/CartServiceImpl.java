package com.naturpoint.service.impl;

import com.naturpoint.dto.AddToCartDTO;
import com.naturpoint.dto.CartDTO;
import com.naturpoint.dto.CartItemDTO;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Cart;
import com.naturpoint.model.Product;
import com.naturpoint.repository.ICartRepository;
import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.repository.IProductRepository;
import com.naturpoint.security.model.User;
import com.naturpoint.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl extends CRUDImpl<Cart, Integer> implements ICartService {

    @Autowired
    private ICartRepository repository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    protected IGenericRepo<Cart, Integer> getRepository() {
        return repository;
    }

    @Override
    public Cart addToCart(AddToCartDTO addToCartDTO, User user) {
        Optional<Product> optionalProduct = productRepository.findById(addToCartDTO.getIdProduct());
        if (optionalProduct.isEmpty()) {
            throw new ModelNotFoundException("PRODUCT WITH ID: " + addToCartDTO.getIdProduct() + " NOT FOUND");
        }
        Cart cart = new Cart();
        cart.setProduct(optionalProduct.get());
        cart.setUser(user);
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setCreatedDate(LocalDateTime.now());

        return repository.save(cart);
    }

    @Override
    public CartDTO listCartItems(User user) {
        Set<Cart> cartList = repository.findAllByUserOrderByCreatedDateDesc(user);
        Set<CartItemDTO> cartItems = new HashSet<>();

        double totalCost = 0;

        for (Cart cart: cartList) {
            CartItemDTO cartItemDTO = new CartItemDTO(cart);
            totalCost += cartItemDTO.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDTO);
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalCost(totalCost);
        cartDTO.setCartItems(cartItems);

        return cartDTO;
    }

    @Override
    public void deleteCartItem(Integer idCartItem, User user) {
        Optional<Cart> optionalCart = repository.findById(idCartItem);
        if (optionalCart.isEmpty()) {
            throw new ModelNotFoundException("Cart item not found: " + idCartItem);
        }

        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw new ModelNotFoundException("Cart item does not belong to user: " + idCartItem);
        }

        repository.delete(cart);
    }
}
