package com.naturpoint.service.impl;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.model.Product;
import com.naturpoint.repository.IGenericRepo;
import com.naturpoint.repository.IProductRepository;
import com.naturpoint.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    protected IGenericRepo<Product, Integer> getRepository() {
        return repository;
    }

    @Override
    public ProductDTO getProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setSn(product.getSn());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setPhotoUrl(product.getPhotoUrl());
        return productDTO;
    }
}
