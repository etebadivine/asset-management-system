package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateCategoryDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/category")
@RestController
public class CategoryController extends ExceptionHandlerController{

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createCategory(@RequestBody CreateCategoryDto createCategoryDto){

        Category category = categoryService.addCategory(
                createCategoryDto.getName(),
                createCategoryDto.getDescription()
        );

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(category);
        return response;
    }


    @GetMapping
    public ApiResponse findAll(){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(categoryService.getAllCategories());
        return response;
    }
}
