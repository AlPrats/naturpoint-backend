package com.naturpoint.controller;

import com.naturpoint.dto.AddToCartDTO;
import com.naturpoint.dto.CartDTO;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Cart;
import com.naturpoint.security.model.User;
import com.naturpoint.security.service.IUserService;
import com.naturpoint.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public ResponseEntity<CartDTO> getCartItems(@RequestParam("username") String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        User user = optionalUser.get();
        CartDTO cartDTO = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartDTO addToCartDTO, @RequestParam("username") String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ModelNotFoundException("USER WITH USERNAME: " + username + " NOT FOUND");
        }

        User user = optionalUser.get();
        Cart cart = cartService.addToCart(addToCartDTO, user);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{idCartItem}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable("idCartItem") Integer idItem, @RequestParam("username") String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        User user = optionalUser.get();
        cartService.deleteCartItem(idItem, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
