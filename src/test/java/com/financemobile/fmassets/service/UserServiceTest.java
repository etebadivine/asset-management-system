package com.financemobile.fmassets.service;

import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import javax.persistence.criteria.Predicate;
import java.util.List;


@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void test_searchUsers() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Sam");
        user.setLastName("Addy");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428114");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        Sort sort = Sort.by(Sort.Direction.DESC,"dateCreated");
        Pageable pageable = PageRequest.of(0,10,sort);

        UserSpec userSpec = (UserSpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("firstName"), user.getFirstName());
            return equal;
        };

        List<User> userList = userService.searchUsers(userSpec, pageable);
        Assertions.assertEquals(userList.size(), 1);
        Assertions.assertEquals(userList.get(0).getFirstName(), user.getFirstName());
        Assertions.assertEquals(userList.get(0).getLastName(), user.getLastName());
        Assertions.assertEquals(userList.get(0).getEmail(), user.getEmail());
    }
}
