package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Department;
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
       department.setCreatedBy("Kevin");
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
}
