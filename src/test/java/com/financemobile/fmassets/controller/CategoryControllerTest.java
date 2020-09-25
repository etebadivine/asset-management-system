package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateCategoryDto;
import com.financemobile.fmassets.dto.EditCategoryDto;
import com.financemobile.fmassets.dto.EditRoleDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.CategoryService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CategoryControllerTest extends OAuth2Helper {


    @MockBean
    private CategoryService categoryService;

    private final Gson gson = new Gson();

    @Test
    public void test_addCategory() throws Exception {

        // mock repo and response
        Category category = new Category();
        category.setId(2L);
        category.setName("Electronic");
        category.setDescription("Microwave, Air conditions etc");
        category.setCreatedBy("Samuel");
        category.setDateCreated(new Date());
        category.setDateModified(new Date());

        Mockito.when(categoryService.addCategory(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(category);

        // payload for the endpoint
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName(category.getName());
        createCategoryDto.setDescription(category.getDescription());

        // fire request
        mockMvc.perform(post("/category")
                .content(gson.toJson(createCategoryDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(category.getId().intValue())))
                .andExpect(jsonPath("data.name", is(category.getName())))
                .andExpect(jsonPath("data.description", is(category.getDescription())));
    }

    @Test
    public void test_findAll() throws Exception {

        // mock repo and response
        Category category = new Category();
        category.setId(2L);
        category.setName("Electronic");
        category.setDescription("Microwave, Air conditions etc");
        category.setCreatedBy("Samuel");
        category.setDateCreated(new Date());
        category.setDateModified(new Date());

        List<Category> categoryList = Arrays.asList(category);

        Mockito.when(categoryService.getAllCategories())
                .thenReturn(categoryList);

        // fire request
        mockMvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(category.getName())))
                .andExpect(jsonPath("$.data[0].description", is(category.getDescription())));
    }

    @Test
    public void test_editCategory() throws Exception {

        Category category = new Category();
        category.setId(300L);
        category.setName("Computense");
        category.setDateCreated(new Date());
        category.setDateModified(new Date());

        Mockito.when(categoryService.editCategory(Mockito.any(EditCategoryDto.class)))
                .thenReturn(category);

        EditCategoryDto editCategoryDto = new EditCategoryDto();
        editCategoryDto.setCategoryId(category.getId());
        editCategoryDto.setName("Computers");

        mockMvc.perform(put("/category")
                .content(gson.toJson(editCategoryDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(category.getId().intValue())))
                .andExpect(jsonPath("data.name", is(category.getName())));
    }
}
