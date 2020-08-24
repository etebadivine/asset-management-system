package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.Role;

import java.util.List;

public interface  RoleService {

    public Role addRole(String name);
    public Role getRoleById(Long id);
    public List<Role> getAllRoles();

}
