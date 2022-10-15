package com.naturpoint.service;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.model.Product;

public interface IProductService extends ICRUD<Product, Integer> {

    ProductDTO getProductDTO(Product product);
}
