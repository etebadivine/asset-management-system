package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.EditAssetDto;
import com.financemobile.fmassets.dto.EditDepartmentDto;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department addDepartment(String name) {
       if(departmentRepository.existsByName(name))
           throw new AlreadyExistException("record already exists");

       Department department = new Department();
       department.setName(name);
       department.setCreatedBy("Divine");
       return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentByName(String name) {
        Optional<Department> departmentOptional =
                departmentRepository.findByName(name);

        if(departmentOptional.isPresent()){
            return departmentOptional.get();
        }

        throw new DataNotFoundException(("record not found"));
    }

    @Override
    public Department getDepartmentById(Long id) {
        Optional<Department> departmentOptional =
                departmentRepository.findById(id);

        if(departmentOptional.isPresent()){
            return departmentOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }

    @Override
    public Department editDepartment(EditDepartmentDto editDepartmentDto) {

        Optional<Department> departmentOptional = departmentRepository.findById(editDepartmentDto.getDepartmentId());
        if (departmentOptional.isPresent()){
            Department department = departmentOptional.get();
            department.setName(editDepartmentDto.getName());

            return departmentRepository.save(department);
        }

        throw new DataNotFoundException("Department not found");
    }

    @Override
    public void removeDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
