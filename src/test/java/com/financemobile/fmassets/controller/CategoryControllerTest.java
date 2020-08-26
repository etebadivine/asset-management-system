package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateCategoryDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.repository.CategoryRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setup(){

    }


    @AfterEach
    public void tearDown(){

    }


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

        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(category);

        // payload for the endpoint
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName(category.getName());
        createCategoryDto.setDescription(category.getDescription());

        // fire request
        mockMvc.perform(post("/category")
                .content(gson.toJson(createCategoryDto))
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

        Mockito.when(categoryRepository.findAll())
                .thenReturn(categoryList);

        // fire request
        mockMvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(category.getName())))
                .andExpect(jsonPath("$.data[0].description", is(category.getDescription())));

    }
}
