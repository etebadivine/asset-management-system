package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.EditRoleDto;
import com.financemobile.fmassets.model.Role;

import java.util.List;

public interface  RoleService {

    public Role addRole(String name);
    public Role getRoleById(Long id);
    public Role getRoleByName(String name);
    public List<Role> getAllRoles();
    public Role editRole(EditRoleDto editRoleDto);
    public void removeRole(Long id);
}
