package com.naturpoint.controller;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Product;
import com.naturpoint.model.Wishlist;
import com.naturpoint.security.model.User;
import com.naturpoint.security.service.IUserService;
import com.naturpoint.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private IWishlistService wishlistService;

    @Autowired
    private IUserService userService;


    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@Valid @RequestBody Product product, @RequestParam("username") String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ModelNotFoundException("USER WITH USERNAME: " + username + " NOT FOUND");
        }
        User user = optionalUser.get();
        Wishlist wishlist = new Wishlist(user, product);
        wishlistService.save(wishlist);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ProductDTO>> getWishlist(@PathVariable("username") String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ModelNotFoundException("USER WITH USERNAME: \" + username + \" NOT FOUND");
        }
        //User user = optionalUser.get();
        List<ProductDTO> productDTOS = wishlistService.getWishlist(username);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
}
