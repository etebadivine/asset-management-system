package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.EditCategoryDto;
import com.financemobile.fmassets.dto.EditDepartmentDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Department;

import java.util.List;


public interface CategoryService {

    Category addCategory(String name, String description);
    Category getCategory(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    void removeCategory(Long id);
    public Category editCategory(EditCategoryDto editCategoryDto);

}
