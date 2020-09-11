package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateDepartmentDto;
import com.financemobile.fmassets.dto.CreateRoleDto;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.service.DepartmentService;
import com.financemobile.fmassets.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/department")
@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController (DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createDepartment (@RequestBody CreateDepartmentDto createDepartmentDto){

        Department department = departmentService.addDepartment(
                createDepartmentDto.getName()

        );

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(department);
        return response;
    }


    @GetMapping
    public ApiResponse findAll(){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(departmentService.getAllDepartments());
        return response;
    }
}
