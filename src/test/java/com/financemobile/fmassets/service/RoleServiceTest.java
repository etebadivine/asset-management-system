package com.financemobile.fmassets.service;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;


    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup(){
        roleRepository.deleteAll();
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void test_addRole(){
        String name = "Manager";
        Role role = roleService.addRole(name);

        Assertions.assertNotNull(role.getId());
        Assertions.assertEquals(role.getName(), name);
        Assertions.assertNotNull(role.getDateCreated());

    }

    @Test
    public void test_addRole_duplicate(){
        String name = "Manager";
        roleService.addRole(name);

        Assertions.assertThrows(AlreadyExistException.class, () -> {
            roleService.addRole(name);
        });
    }

    @Test
    public void test_getRoleById(){
        String name = "Management";
        Long id = 204L;
        Role role = roleService.addRole(name);




        Role role1 = roleService.getRoleById(role.getId());
        Assertions.assertEquals(role.getId(), role1.getId());


    }

    @Test
    public void test_getRoleById_notfound() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            roleService.getRoleById(204L);
        });

    }

    @Test
    public void test_getAllRoles(){
        String name = "Management";
        Role role = roleService.addRole(name);

        List<Role> roleList = roleService.getAllRoles();

        Assertions.assertEquals(roleList.size(), 1);
        Assertions.assertEquals(roleList.get(0).getName(), name);
    }

}