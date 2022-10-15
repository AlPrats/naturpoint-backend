package com.naturpoint.dto.cart;

import com.naturpoint.model.Cart;
import com.naturpoint.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Integer idCartItem;
    private Integer quantity;
    private Product product;

    public CartItemDTO(Cart cart) {
        this.idCartItem = cart.getIdCart();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
