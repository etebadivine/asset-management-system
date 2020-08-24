package com.financemobile.fmassets.service;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setup(){
        departmentRepository.deleteAll();
    }

    @AfterEach
    public void teardown(){

    }

    @Test
    public void test_addDepartment(){

        String name = "Human Resource";
        Long numberOfAssets = 1L;
        Department department = departmentService.addDepartment(name, numberOfAssets);

        Assertions.assertNotNull(department.getId());
        Assertions.assertEquals(department.getName(), name);
        Assertions.assertEquals(department.getNumberOfAssets(), numberOfAssets);
        Assertions.assertNotNull(department.getCreatedBy());
        Assertions.assertNotNull(department.getDateCreated());

    }

    @Test
    public void test_addDepartment_duplicate(){
        String name = "Human Resource";
        Long numberOfAssets = 1L;
        departmentService.addDepartment(name, numberOfAssets);

        Assertions.assertThrows(AlreadyExistException.class, () ->{
            departmentService.addDepartment(name,1L );
        });
    }

    @Test
    public void test_getDepartmentByName(){

        String name = "Human Resource";
        Long numberOfAssets = 1L;
        Department department = departmentService.addDepartment(name, numberOfAssets);

        Department dept = departmentService.getDepartmentByName(name);
        Assertions.assertEquals(department.getId(), dept.getId());
        Assertions.assertEquals(department.getName(), dept.getName());
        Assertions.assertEquals(department.getNumberOfAssets(), dept.getNumberOfAssets());
//        Assertions.assertNotNull(department.getCreatedBy());
//        Assertions.assertNotNull(department.getDateCreated());

    }

    @Test
    public void test_getDepartmentByName_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () ->{
            departmentService.getDepartmentByName("Cleaners");
        });

    }

    @Test
    public void test_getDepartmentById(){

        String name = "Human Resource";
        Long numberOfAssets = 1L;
        Department department = departmentService.addDepartment(name, numberOfAssets);

        Department dept = departmentService.getDepartmentByName(name);
        Assertions.assertEquals(department.getId(), dept.getId());
        Assertions.assertEquals(department.getName(), name);
        Assertions.assertEquals(department.getNumberOfAssets(), numberOfAssets);
    }

    @Test
    public void test_getDepartmentId_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () ->{
            departmentService.getDepartmentById(3L);
        });

    }

    @Test
    public void test_getAllDepartments(){

        String name = "Human Resource";
        Long numberOfAssets = 1L;
        Department department = departmentService.addDepartment(name, numberOfAssets);

        List<Department> departmentList = departmentService.getAllDepartments();

        Assertions.assertEquals(departmentList.size(), 1);
        Assertions.assertEquals(departmentList.get(0).getName(), name);
        Assertions.assertEquals(departmentList.get(0).getNumberOfAssets(), numberOfAssets);
    }

}
