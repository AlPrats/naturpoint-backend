package com.naturpoint.repository;

import com.naturpoint.model.Category;

public interface ICategoryRepository extends IGenericRepo<Category, Integer> {

    Category findByName(String name);
}
