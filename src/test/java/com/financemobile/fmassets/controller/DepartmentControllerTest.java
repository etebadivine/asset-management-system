package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateDepartmentDto;
import com.financemobile.fmassets.dto.CreateRoleDto;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.repository.RoleRepository;
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
public class DepartmentControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private DepartmentRepository departmentRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setup(){

    }


    @AfterEach
    public void tearDown(){

    }


    @Test
    public void test_addDepartment() throws Exception {

        // mock repo and response
        Department department = new Department();
        department.setId(2L);
        department.setName("Electronic");
        department.setDateCreated(new Date());


        Mockito.when(departmentRepository.save(Mockito.any(Department.class)))
                .thenReturn(department);

        // payload for the endpoint
        CreateDepartmentDto createDepartmentDto = new CreateDepartmentDto();
        createDepartmentDto.setName(department.getName());


        // fire request
        mockMvc.perform(post("/department")
                .content(gson.toJson(createDepartmentDto))
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

        Mockito.when(departmentRepository.findAll())
                .thenReturn(departmentList);

        // fire request
        mockMvc.perform(get("/department")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(department.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(department.getName())));


    }
}
