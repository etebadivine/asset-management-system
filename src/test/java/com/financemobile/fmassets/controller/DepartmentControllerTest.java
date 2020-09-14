package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateDepartmentDto;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.DepartmentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentControllerTest extends OAuth2Helper {


    @MockBean
    private DepartmentService departmentService;

    private final Gson gson = new Gson();

    @Test
    public void test_addDepartment() throws Exception {

        // mock repo and response
        Department department = new Department();
        department.setId(2L);
        department.setName("Electronic");
        department.setDateCreated(new Date());

        Mockito.when(departmentService.addDepartment(Mockito.anyString()))
                .thenReturn(department);

        // payload for the endpoint
        CreateDepartmentDto createDepartmentDto = new CreateDepartmentDto();
        createDepartmentDto.setName(department.getName());


        // fire request
        mockMvc.perform(post("/department")
                .content(gson.toJson(createDepartmentDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(department.getId().intValue())))
                .andExpect(jsonPath("data.name", is(department.getName())));

    }

    @Test
    public void test_findAll() throws Exception {

        // mock repo and response
        Department department = new Department();
        department.setId(2L);
        department.setName("Electronic");
        department.setDateCreated(new Date());

        List<Department> departmentList = Arrays.asList(department);

        Mockito.when(departmentService.getAllDepartments())
                .thenReturn(departmentList);

        // fire request
        mockMvc.perform(get("/department")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(department.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(department.getName())));
    }
}