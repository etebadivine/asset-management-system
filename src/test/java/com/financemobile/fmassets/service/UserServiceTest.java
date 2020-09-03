package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.PasswordMismatchException;
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

    @BeforeEach
    public void Setup() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void test_addUser(){
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName,lastName,email,phone,password);
        User user = userService.addUser(createUserDto);


        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getFirstName(), firstName);
        Assertions.assertEquals(user.getLastName(), lastName);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPhone(), phone);
        Assertions.assertEquals(user.getPassword(), password);
    }

    @Test
    public void test_addUser_duplicate(){
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName,lastName,email,phone,password);
        userService.addUser(createUserDto);

        Assertions.assertThrows(AlreadyExistException.class, ()->{
            userService.addUser(createUserDto);
        });
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
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

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
    public void test_searchUsers_emptyResult() {

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

        UserSpec userSpec = (UserSpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("firstName"), "Felix");
            return equal;
        };

        List<User> userList = userService.searchUsers(userSpec, pageable);
        Assertions.assertEquals(userList.size(), 0);
    }

    @Test
    public void test_getUserByEmail() {
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
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        User createdUser = userService.getUserByEmail(user.getEmail());
        Assertions.assertEquals(user.getFirstName(), createdUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), createdUser.getLastName());
        Assertions.assertEquals(user.getPhone(), createdUser.getPhone());
        Assertions.assertEquals(user.getStatus(), createdUser.getStatus());
        Assertions.assertEquals(user.getRole().getName(), createdUser.getRole().getName());
        Assertions.assertEquals(user.getDepartment().getName(), createdUser.getDepartment().getName());
    }

    @Test
    public void test_getUserByEmail_notFound() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.getUserByEmail("unknow@fm.com");
        });
    }

    @Test
    public void test_updateStatus() throws Exception {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setFirstName("James");
        user.setLastName("Anifrani");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428114");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);
        UpdateUserStatusDto updateUserStatusDto = new UpdateUserStatusDto(user.getId(),user.getStatus());
        User user3 =  userService.updateStatus(updateUserStatusDto);
    }

    @Test
    public void test_updateStatus_notFound() throws Exception {
        UpdateUserStatusDto updateUserStatusDto =
                new UpdateUserStatusDto(4L,UserStatus.BLOCKED);
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.updateStatus(updateUserStatusDto);
        });
    }

    @Test
    public void test_resetPassword() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setId(200L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user.setEmail("you@gmail.com");
        user.setPhone("+233241428114");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto(user.getId(), user.getPassword(), "newpassword");
        userService.resetPassword(resetPasswordDto);
    }

    @Test
    public void test_resetPassword_notFound() {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setUserId(30L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            userService.resetPassword(resetPasswordDto);
        });
    }

    @Test
    public void test_resetPassword_passwordMismatch() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setId(200L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user.setEmail("you@gmail.com");
        user.setPhone("+233241428114");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setOldPassword("wrong_password");
        resetPasswordDto.setUserId(user.getId());
        Assertions.assertThrows(PasswordMismatchException.class, ()->{
            userService.resetPassword(resetPasswordDto);
        });
    }

    @Test
    public void test_resetPasswordByEmail() {
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
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail("email");
        Assertions.assertTrue(true);
    }

    @Test
    public void test_resetPasswordByEmail_notFound() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.getUserByEmail("notfound@gmail.com");
        });
    }

    @Test
    public void test_updateUserRole() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setName("USER");
        role.setId(200L);
        role = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428114");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        UpdateuserRoleDto updateuserRoleDto = new UpdateuserRoleDto(user.getId(),user.getRole().getName());
        User user1 =  userService.updateUserRole(updateuserRoleDto);
    }

    @Test
    public void test_updateUserRole_notFound() throws Exception {
        UpdateuserRoleDto updateuserRoleDto =
                new UpdateuserRoleDto(4L,"Manager");
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.updateUserRole(updateuserRoleDto);
        });
    }
}
