package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.EditRoleDto;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.service.RoleService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(String name) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (roleRepository.existsByName(name))
            throw new AlreadyExistException("Role already exists");

        Role role = new Role();
        role.setName (name);
        role.setCreatedBy(authentication.getName());
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id) throws DataNotFoundException {
        Optional<Role> roleOptional =roleRepository.findById(id);

        if(roleOptional.isPresent()){
            return roleOptional.get();
        }

        throw new DataNotFoundException("Role not found");
    }

    @Override
    public Role getRoleByName(String name) {
        Optional<Role> roleOptional =
                roleRepository.findByName(name);

        if(roleOptional.isPresent()){
            return roleOptional.get();
        }
        throw new DataNotFoundException("Role not found");
    }

    @Override
    public List<Role> getAllRoles() {
      return roleRepository.findAll();
    }

    @Override
    public Role editRole(EditRoleDto editRoleDto) {

        Optional<Role> roleOptional = roleRepository.findById(editRoleDto.getRoleId());
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            role.setName(editRoleDto.getName());

            return roleRepository.save(role);
        }
        throw new DataNotFoundException("Role not found");
    }

    @Override
    public void removeRole(Long id) {
        roleRepository.deleteById(id);
    }
}
