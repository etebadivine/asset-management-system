package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.Department;

import java.util.List;

public interface DepartmentService {
    public Department addDepartment(String name);
    public Department getDepartmentByName(String name);
    public Department getDepartmentById(Long id);
    public List<Department> getAllDepartments();
}
