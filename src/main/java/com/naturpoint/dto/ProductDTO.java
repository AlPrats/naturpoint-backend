package com.naturpoint.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Integer idProduct;
    private Integer sn;
    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull int stock;
    private @NotNull String photoUrl;
    private @NotNull Integer idCategory;
}
