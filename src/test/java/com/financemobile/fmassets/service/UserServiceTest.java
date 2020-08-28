package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.CreateUserDto;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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

    @Test
    public void test_searchByEmail() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
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
                    builder.equal(root.get("email"), user.getEmail());
            return equal;
        };
        CreateUserDto createUserDto = new CreateUserDto();

        User user1 = userService.getUserByEmail(user.getEmail());
        Assertions.assertEquals(user.getFirstName(), user.getFirstName());
        Assertions.assertEquals(user.getLastName(), user.getLastName());
        Assertions.assertEquals(user1.getEmail(), user1.getEmail());

    }

//    @Test
//    public void test_addUser(){
//        String firstName = "Felix";
//        String lastName = "Duedu";
//        String  email= "me@gmail.com";
//        String  phone= "+233 54 567 4665";
//        CreateUserDto createUserDto = new CreateUserDto();
//        createUserDto.setEmail("me@gmail.com");
//        User user =  userService.addUser(createUserDto);
//
//        System.out.println("createUserDto: " + user);
//
//        Assertions.assertNotNull(user.getId());
//        Assertions.assertEquals(user.getFirstName(), firstName);
//        Assertions.assertEquals(user.getLastName(),  lastName);
//        Assertions.assertEquals(user.getEmail(), email);
//        Assertions.assertEquals(user.getPhone(), phone);
//    }
//
//    @Test
//    public void test_getUserByEmail(){
//        String  email= "me@gmail.com";
//        CreateUserDto createSupplierDto = new CreateUserDto(email);
//        User user =  userService.addUser(createSupplierDto);
//
//        User usr = userService.getUserByEmail(email);
//        Assertions.assertEquals(user.getId(), usr.getId());
//        Assertions.assertEquals(user.getFirstName(), usr.getFirstName());
//        Assertions.assertEquals(user.getLastName(), usr.getLastName());
//        Assertions.assertEquals(user.getEmail(), usr.getEmail());
//
//    }
//


}
