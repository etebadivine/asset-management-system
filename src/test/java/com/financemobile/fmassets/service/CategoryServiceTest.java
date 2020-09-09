package com.financemobile.fmassets.service;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test_addCategory(){

        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        Category category = categoryService.addCategory(name, description);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(category.getName(), name);
        Assertions.assertEquals(category.getDescription(), description);
        Assertions.assertNotNull(category.getCreatedBy());
        Assertions.assertNotNull(category.getDateCreated());
    }

    @Test
    public void test_addCategory_duplicate(){
        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        categoryService.addCategory(name, description);

        Assertions.assertThrows(AlreadyExistException.class, () -> {
            categoryService.addCategory(name, "furniture furniture");
        });
    }

    @Test
    public void test_getCategory(){
        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        Category category = categoryService.addCategory(name, description);

        Category ctgry = categoryService.getCategory(category.getId());
        Assertions.assertEquals(category.getId(), ctgry.getId());
        Assertions.assertEquals(category.getName(), name);
        Assertions.assertEquals(category.getDescription(), description);

    }

    @Test
    public void test_getCategory_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            categoryService.getCategory(4L);
        });
    }

    @Test
    public void test_getCategoryByName(){
        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        Category category = categoryService.addCategory(name, description);

        Category ctgry = categoryService.getCategoryByName(name);
        Assertions.assertEquals(category.getId(), ctgry.getId());
        Assertions.assertEquals(category.getName(), ctgry.getName());
        Assertions.assertEquals(category.getDescription(), ctgry.getDescription());

    }

    @Test
    public void test_getCategoryByName_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            categoryService.getCategoryByName("Electronics");
        });
    }

    @Test
    public void test_getAllCategories(){
        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        Category category = categoryService.addCategory(name, description);

        List<Category> categoryList = categoryService.getAllCategories();

        Assertions.assertEquals(categoryList.size(), 1);
        Assertions.assertEquals(categoryList.get(0).getName(), name);
        Assertions.assertEquals(categoryList.get(0).getDescription(), description);
    }

    @Test
    public void test_removeCategory(){
        String name = "Furniture";
        String description = "Chairs, Tables, Desks, Cabinets etc";
        Category category = categoryService.addCategory(name, description);

        categoryService.removeCategory(category.getId());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            categoryService.getCategoryByName(name);
        });
    }


    //this should always be the last method
    @AfterEach
    public void tearDown(){
        categoryRepository.deleteAll();
    }
}
