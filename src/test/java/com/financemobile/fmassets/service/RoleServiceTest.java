package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.List;

@SpringBootTest
public class RoleServiceTest {


    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithAnonymousUser
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

    @Test
    public void test_editRole() {
        String name = "USER";
        CreateRoleDto createRoleDto = new CreateRoleDto();
        createRoleDto.setName(name);
        Role role = roleService.addRole(createRoleDto.getName());

        EditRoleDto editRoleDto = new EditRoleDto();
        editRoleDto.setRoleId(role.getId());
        editRoleDto.setName("ADMIN");

        Role editedRole = roleService.editRole(editRoleDto);
        Assertions.assertNotNull(editedRole.getId());
        Assertions.assertEquals(editedRole.getName(), editRoleDto.getName());
    }

    @Test
    public void test_editRole_notFound(){
        EditRoleDto editRoleDto = new EditRoleDto();
        editRoleDto.setRoleId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            roleService.editRole(editRoleDto);
        });
    }

    @Test
    public void test_removeRole() {
        String name = "Manager";
        Role role = roleService.addRole(name);
        roleService.removeRole(role.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            roleService.getRoleByName(name);
        });
    }

    @AfterEach
    public void tearDown(){
        roleRepository.deleteAll();
    }
}