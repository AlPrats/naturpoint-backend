package com.naturpoint.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Set<CartItemDTO> cartItems;
    private double totalCost;
}
