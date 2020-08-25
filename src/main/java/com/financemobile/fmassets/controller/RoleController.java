package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateCategoryDto;
import com.financemobile.fmassets.dto.CreateRoleDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.service.CategoryService;
import com.financemobile.fmassets.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/role")
@RestController
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController (RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createRole (@RequestBody CreateRoleDto createRoleDto){

        Role role = roleService.addRole(
                createRoleDto.getName()
        );

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(role);
        return response;
    }


    @GetMapping
    public ApiResponse findAll(){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(roleService.getAllRoles());
        return response;
    }
}
