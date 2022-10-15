package com.naturpoint.controller;

import com.naturpoint.dto.ProductDTO;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Category;
import com.naturpoint.model.Product;
import com.naturpoint.service.ICategoryService;
import com.naturpoint.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> list = productService.findAll()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Integer id) {
        /*Optional<Product> optionalProduct = Optional.ofNullable(productService.findById(id));
        ProductDTO dtoResponse;

        if (optionalProduct.isEmpty()) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(optionalProduct, ProductDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);*/
        ProductDTO productDTO;
        Product obj = productService.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            productDTO = mapper.map(obj, ProductDTO.class);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO productDTO) {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryService.findById(productDTO.getIdCategory()));
        if (optionalCategory.isEmpty()) {
            throw new ModelNotFoundException("ID CATEGORY NOT FOUND: " + productDTO.getIdCategory());
        }
        Product product = productService.save(mapper.map(productDTO, Product.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getIdProduct()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Product> update(@Valid @RequestBody ProductDTO productDTO) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.findById(productDTO.getIdProduct()));
        if (optionalProduct.isEmpty()) {
            throw new ModelNotFoundException("ID PRODUCT NOT FOUND: " + productDTO.getIdProduct());
        }
        return new ResponseEntity<>(productService.update(mapper.map(productDTO, Product.class)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.findById(id));
        if (optionalProduct.isEmpty()) {
            throw new ModelNotFoundException("ID PRODUCT NOT FOUND: " + id);
        } else {
            productService.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}











