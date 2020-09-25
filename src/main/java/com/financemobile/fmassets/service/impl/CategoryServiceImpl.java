package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.EditCategoryDto;
import com.financemobile.fmassets.dto.EditDepartmentDto;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.repository.CategoryRepository;
import com.financemobile.fmassets.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(String name, String description) {

        if(categoryRepository.existsByName(name))
            throw new AlreadyExistException("record already exists");

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setCreatedBy("Sam");// change this later
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) throws DataNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public Category getCategoryByName(String name) {
        Optional<Category> categoryOptional =
                categoryRepository.findByName(name);

        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category editCategory(EditCategoryDto editCategoryDto) {

        Optional<Category> categoryOptional = categoryRepository.findById(editCategoryDto.getCategoryId());
        if (categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            category.setName(editCategoryDto.getName());

            return categoryRepository.save(category);
        }
        throw new DataNotFoundException("Category not found");
    }


    @Override
    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
