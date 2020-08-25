package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateCategoryDto;
import com.financemobile.fmassets.dto.CreateRoleDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.CategoryRepository;
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
public class RoleControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private RoleRepository roleRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setup(){

    }


    @AfterEach
    public void tearDown(){

    }


    @Test
    public void test_addRole() throws Exception {

        // mock repo and response
        Role role = new Role();
        role.setId(2L);
        role.setName("Electronic");
        role.setDateCreated(new Date());


        Mockito.when(roleRepository.save(Mockito.any(Role.class)))
                .thenReturn(role);

        // payload for the endpoint
        CreateRoleDto createRoleDto = new CreateRoleDto();
        createRoleDto.setName(role.getName());


        // fire request
        mockMvc.perform(post("/role")
                .content(gson.toJson(createRoleDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(role.getId().intValue())))
                .andExpect(jsonPath("data.name", is(role.getName())));

    }

    @Test
    public void test_findAll() throws Exception {

        // mock repo and response
        Role role = new Role();
        role.setId(2L);
        role.setName("Electronic");
        role.setDateCreated(new Date());


        List<Role> roleList = Arrays.asList(role);

        Mockito.when(roleRepository.findAll())
                .thenReturn(roleList);

        // fire request
        mockMvc.perform(get("/role")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(role.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(role.getName())));


    }
}
