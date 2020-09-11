package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateRoleDto;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.RoleService;
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

public class RoleControllerTest extends OAuth2Helper {

    @MockBean
    private RoleService roleService;

    private final Gson gson = new Gson();

    @Test
    public void test_addRole() throws Exception {

        // mock repo and response
        Role role = new Role();
        role.setId(2L);
        role.setName("ADMIN");
        role.setDateCreated(new Date());


        Mockito.when(roleService.addRole(role.getName()))
                .thenReturn(role);

        // payload for the endpoint
        CreateRoleDto createRoleDto = new CreateRoleDto();
        createRoleDto.setName(role.getName());


        mockMvc.perform(post("/role")
                .content(gson.toJson(createRoleDto))
                .header("Authorization", "Bearer " + accessToken)
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
        role.setName("ADMIN");
        role.setDateCreated(new Date());


        List<Role> roleList = Arrays.asList(role);

        Mockito.when(roleService.getAllRoles())
                .thenReturn(roleList);

        // fire request
        mockMvc.perform(get("/role")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(role.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(role.getName())));
    }
}
