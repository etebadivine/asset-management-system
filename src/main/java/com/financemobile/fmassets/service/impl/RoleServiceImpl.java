package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(String name) {
        if (roleRepository.existsByName(name))
            throw new AlreadyExistException("role already exists");

        Role role = new Role();
        role.setName (name);
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id) throws DataNotFoundException {
        Optional<Role> roleOptional =roleRepository.findById(id);

        if(roleOptional.isPresent()){
            return roleOptional.get();
        }

        throw new DataNotFoundException("role not found");
    }

    @Override
    public List<Role> getAllRoles() {
      return roleRepository.findAll();
    }

}
