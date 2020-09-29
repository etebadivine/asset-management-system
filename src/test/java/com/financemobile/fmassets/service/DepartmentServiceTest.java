package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.List;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @WithAnonymousUser
    public void test_addDepartment(){
        String name = "Human Resource";
        Department department = departmentService.addDepartment(name);

        Assertions.assertNotNull(department.getId());
        Assertions.assertEquals(department.getName(), name);
        Assertions.assertEquals(department.getNumberOfAssets(), 0L);
        Assertions.assertNotNull(department.getCreatedBy());
        Assertions.assertNotNull(department.getDateCreated());
    }

    @Test
    @WithAnonymousUser
    public void test_addDepartment_duplicate(){
        String name = "Human Resource";
        departmentService.addDepartment(name);

        Assertions.assertThrows(AlreadyExistException.class, () ->{
            departmentService.addDepartment(name);
        });
    }

    @Test
    @WithAnonymousUser
    public void test_getDepartmentByName(){
        String name = "Human Resource";
        Department department = departmentService.addDepartment(name);

        Department dept = departmentService.getDepartmentByName(name);
        Assertions.assertEquals(department.getId(), dept.getId());
        Assertions.assertEquals(department.getName(), dept.getName());
        Assertions.assertEquals(department.getNumberOfAssets(), dept.getNumberOfAssets());
    }

    @Test
    public void test_getDepartmentByName_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () ->{
            departmentService.getDepartmentByName("Cleaners");
        });
    }

    @Test
    @WithAnonymousUser
    public void test_getDepartmentById(){
        String name = "Human Resource";
        Department department = departmentService.addDepartment(name);

        Department dept = departmentService.getDepartmentById(department.getId());
        Assertions.assertEquals(department.getId(), dept.getId());
        Assertions.assertEquals(department.getName(), name);
        Assertions.assertEquals(department.getNumberOfAssets(), 0L);
    }

    @Test
    public void test_getDepartmentId_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () ->{
            departmentService.getDepartmentById(3L);
        });
    }

    @Test
    @WithAnonymousUser
    public void test_getAllDepartments(){
        String name = "Human Resource";
        Department department = departmentService.addDepartment(name);

        List<Department> departmentList = departmentService.getAllDepartments();

        Assertions.assertEquals(departmentList.size(), 1);
        Assertions.assertEquals(departmentList.get(0).getName(), name);
        Assertions.assertEquals(departmentList.get(0).getNumberOfAssets(), 0L);
    }

    @Test
    @WithAnonymousUser
    public void test_editDepartment() {
        String name = "Communicationssss";
        CreateDepartmentDto createDepartmentDto = new CreateDepartmentDto();
        createDepartmentDto.setName(name);
        Department department = departmentService.addDepartment(createDepartmentDto.getName());

        EditDepartmentDto editDepartmentDto = new EditDepartmentDto();
        editDepartmentDto.setDepartmentId(department.getId());
        editDepartmentDto.setName("Communication");

        Department editedDepartment = departmentService.editDepartment(editDepartmentDto);

        Assertions.assertNotNull(editedDepartment.getId());
        Assertions.assertEquals(editedDepartment.getName(),editDepartmentDto.getName());
    }

    @Test
    public void test_editDepartment_notFound(){
        EditDepartmentDto editDepartmentDto = new EditDepartmentDto();
        editDepartmentDto.setDepartmentId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            departmentService.editDepartment(editDepartmentDto);
        });
    }

    @Test
    @WithAnonymousUser
    public void test_removeDepartment() {
        String name = "Communications";
        Department department = departmentService.addDepartment(name);
        departmentService.removeDepartment(department.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            departmentService.getDepartmentByName(name);
        });
    }

    @AfterEach
    public void tearDown(){
        departmentRepository.deleteAll();
    }
}
