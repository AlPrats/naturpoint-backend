package com.naturpoint.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToCartDTO {

    private Integer idCart;
    private Integer idProduct;
    private Integer quantity;
}
