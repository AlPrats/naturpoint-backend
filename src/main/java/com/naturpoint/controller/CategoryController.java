package com.naturpoint.controller;

import com.naturpoint.dto.CategoryDTO;
import com.naturpoint.exception.CategoryFoundException;
import com.naturpoint.exception.ModelNotFoundException;
import com.naturpoint.model.Category;
import com.naturpoint.service.ICategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll()
                .stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Integer id) {
        /*CategoryDTO dtoResponse;

        Optional<Category> optionalCategory = Optional.ofNullable(service.findById(id));
        if (optionalCategory.isEmpty()) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(optionalCategory, CategoryDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);*/
        CategoryDTO dtoResponse;
        Category obj = service.findById(id);
        if (obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, CategoryDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = Optional.ofNullable(service.findByName(categoryDTO.getName()));
        if (optionalCategory.isPresent()) {
            throw new CategoryFoundException("CATEGORY ALREADY EXISTS: " + categoryDTO.getName());
        }
        Category category = service.save(mapper.map(categoryDTO, Category.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getIdCategory()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Category> update(@Valid @RequestBody CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = Optional.ofNullable(service.findById(categoryDTO.getIdCategory()));
        if (optionalCategory.isEmpty()) {
            throw new ModelNotFoundException("ID NOT FOUND: " + categoryDTO.getIdCategory());
        }
        return new ResponseEntity<>(service.update(mapper.map(categoryDTO, Category.class)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Optional<Category> optionalCategory = Optional.ofNullable(service.findById(id));
        if (optionalCategory.isEmpty()) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}























