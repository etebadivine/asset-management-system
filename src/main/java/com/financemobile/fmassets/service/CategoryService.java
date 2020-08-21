package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(String name, String description);
    Category getCategory(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    void removeCategory(Long id);

}
