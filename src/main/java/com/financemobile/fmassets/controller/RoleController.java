package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.service.CategoryService;
import com.financemobile.fmassets.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/role")
@RestController
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createRole(@RequestBody @Valid CreateRoleDto createRoleDto) {

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
    public ApiResponse findAll() {
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(roleService.getAllRoles());
        return response;
    }


    @PutMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse editRole(@RequestBody @Valid EditRoleDto editRoleDto) {

        Role role = roleService.editRole(editRoleDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(role);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse removeRole(@PathVariable Long id) {

        roleService.removeRole(id);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData("Deleted");

        return response;

    }
}
