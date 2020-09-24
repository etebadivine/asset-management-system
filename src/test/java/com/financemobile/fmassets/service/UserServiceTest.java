package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.PasswordMismatchException;
import com.financemobile.fmassets.model.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void test_addUser() {
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName, lastName, email, phone, password);
        User user = userService.addUser(createUserDto);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getFirstName(), firstName);
        Assertions.assertEquals(user.getLastName(), lastName);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPhone(), phone);

    }

    @Test
    public void test_addUser_duplicate() {
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName, lastName, email, phone, password);
        userService.addUser(createUserDto);

        Assertions.assertThrows(AlreadyExistException.class, () -> {
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
    public void test_getUserById() {
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        userRepository.save(user);

        User createdUser = userService.getUserById(user.getId());
        Assertions.assertEquals(user.getFirstName(), createdUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), createdUser.getLastName());
        Assertions.assertEquals(user.getPhone(), createdUser.getPhone());
        Assertions.assertEquals(user.getStatus(), createdUser.getStatus());
        Assertions.assertEquals(user.getRole().getName(), createdUser.getRole().getName());
        Assertions.assertEquals(user.getDepartment().getName(), createdUser.getDepartment().getName());
    }

    @Test
    public void test_getUserById_notFound() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.getUserById(4L);
        });
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
        user.setPassword(passwordEncoder.encode("password"));
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        UpdateUserStatusDto updateUserStatusDto = new UpdateUserStatusDto();
        updateUserStatusDto.setUserId(user.getId());
        updateUserStatusDto.setStatus(UserStatus.BLOCKED);

        User updatedUser = userService.updateStatus(updateUserStatusDto);
        Assertions.assertEquals(updatedUser.getId(), updateUserStatusDto.getUserId());
        Assertions.assertEquals(updatedUser.getStatus(), updateUserStatusDto.getStatus());
    }

    @Test
    public void test_updateStatus_notFound() throws Exception {
        UpdateUserStatusDto updateUserStatusDto =
                new UpdateUserStatusDto(4L, UserStatus.BLOCKED);
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        ResetPasswordDto resetPasswordDto =
                new ResetPasswordDto(user.getId(), "password", "newpassword");
        user = userService.resetPassword(resetPasswordDto);
        Assertions.assertTrue(passwordEncoder
                .matches(resetPasswordDto.getNewPassword(), user.getPassword()));
    }

    @Test
    public void test_resetPassword_notFound() {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setUserId(30L);
        Assertions.assertThrows(DataNotFoundException.class, () -> {
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setOldPassword("wrong_password");
        resetPasswordDto.setUserId(user.getId());
        Assertions.assertThrows(PasswordMismatchException.class, () -> {
            userService.resetPassword(resetPasswordDto);
        });
    }

    @Test
    public void test_forgotPassword() {
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail(user.getEmail());

        boolean emailExist = userService.forgotPassword(forgotPasswordDto);
        Assertions.assertTrue(emailExist);
    }

    @Test
    public void test_forgotPassword_notFound() {
        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail("notfound@gmail.com");
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.forgotPassword(forgotPasswordDto);
        });
    }

    @Test
    public void test_updateUserRole() {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Role role = new Role();
        role.setId(200L);
        role.setName("USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Divine");
        user.setLastName("Eteba");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428114");
        user.setPassword(passwordEncoder.encode("password"));
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user = userRepository.save(user);

        Role updatedRole = new Role();
        updatedRole.setId(200L);
        updatedRole.setName("CTO");
        updatedRole = roleRepository.save(updatedRole);

        UpdateUserRoleDto updateuserRoleDto = new UpdateUserRoleDto();
        updateuserRoleDto.setUserId(user.getId());
        updateuserRoleDto.setRole(updatedRole.getName());

        User updatedUser = userService.updateUserRole(updateuserRoleDto);
        Assertions.assertNotNull(updateuserRoleDto.getUserId());
        Assertions.assertEquals(updatedUser.getRole().getName(), updateuserRoleDto.getRole());
    }

    @Test
    public void test_updateUserRole_notFound() throws Exception {
        UpdateUserRoleDto updateuserRoleDto = new UpdateUserRoleDto();
        updateuserRoleDto.setRole("CTO");
        updateuserRoleDto.setUserId(200L);
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.updateUserRole(updateuserRoleDto);
        });
    }

    @Test
    public void test_userInvite() {

        UserInviteDto userInviteDto = new UserInviteDto();
        userInviteDto.setEmail("divine@gmail.com");
        userInviteDto.setName("divine");

        boolean sendEmail = userService.sendUserInvite(userInviteDto);
        Assertions.assertTrue(sendEmail);
    }

    @Test
    public void test_userInvite_notFound() {
        User userEmail = new User();
        userEmail.setEmail("kofidvyn@gmail.com");
        userRepository.save(userEmail);

        UserInviteDto userInviteDto = new UserInviteDto();
        userInviteDto.setEmail(userEmail.getEmail());
        Assertions.assertThrows(AlreadyExistException.class, () -> {
            userService.sendUserInvite(userInviteDto);
        });
    }


    @Test
    public void test_editUser() {
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName, lastName, email, phone, password);
        User user = userService.addUser(createUserDto);

        Role role = new Role();
        role.setName("USER");
        role = roleRepository.save(role);

        EditUserDto editUserDto = new EditUserDto();
        editUserDto.setUserId(user.getId());
        editUserDto.setFirstName("Kwasi");
        editUserDto.setLastName("Adanu");
        editUserDto.setEmail("kwasi@gmail.com");
        editUserDto.setPhone("0269277920");
        editUserDto.setRole(role.getName());
        editUserDto.setPassword("password");

        User user1 = userService.editUser(editUserDto);

        Assertions.assertNotNull(user1.getId());
        Assertions.assertEquals(user1.getFirstName(), editUserDto.getFirstName());
        Assertions.assertEquals(user1.getLastName(), editUserDto.getLastName());
        Assertions.assertEquals(user1.getEmail(), editUserDto.getEmail());
        Assertions.assertEquals(user1.getPhone(), editUserDto.getPhone());
        Assertions.assertEquals(user1.getRole(), editUserDto.getRole());
        Assertions.assertEquals(user1.getPassword(), editUserDto.getPassword());
    }

    @Test
    public void test_editUser_notFound(){
        EditUserDto editUserDto = new EditUserDto();
        editUserDto.setUserId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            userService.editUser(editUserDto);
        });
    }

    @Test
    public void test_removeUser() {
        String firstName = "Reynolds";
        String lastName = "Adanu";
        String email = "you@gmail.com";
        String phone = "+233240456008";
        String password = "password";
        CreateUserDto createUserDto = new CreateUserDto(firstName, lastName, email, phone, password);
        User user = userService.addUser(createUserDto);

        userService.removeUser(user.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            userService.getUserByEmail(email);
        });
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }
}


