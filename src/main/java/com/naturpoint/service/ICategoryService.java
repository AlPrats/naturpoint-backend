package com.naturpoint.service;

import com.naturpoint.model.Category;

public interface ICategoryService extends ICRUD<Category, Integer> {

    Category findByName(String name);
}
