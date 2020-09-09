package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends OAuth2Helper {

    @MockBean
    private UserService userService;

    private final Gson gson = new Gson();

    @Test
    public void test_addUser() throws Exception {

        // mock repo and response
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user.setEmail("you@gmail.com");
        user.setPhone("+233240456008");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user.setCreatedBy("Reynolds");
        user.setDateCreated(new Date());
        user.setDateModified(new Date());

        Mockito.when(userService.addUser(Mockito.any(CreateUserDto.class)))
                .thenReturn(user);

        // payload for the endpoint
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setFirstName(user.getFirstName());
        createUserDto.setLastName(user.getLastName());
        createUserDto.setEmail(user.getEmail());
        createUserDto.setPassword(user.getPassword());

        // fire request
        mockMvc.perform(post("/user")
                .content(gson.toJson(createUserDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("data.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("data.lastName", is(user.getLastName())))
                .andExpect(jsonPath("data.email", is(user.getEmail())))
                .andExpect(jsonPath("data.password", is(user.getPassword())));
    }

    @Test
    public void test_searchUsers() throws Exception {
        //create dummy user
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428119");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);

        Mockito.when(userService.searchUsers(Mockito.any(UserSpec.class), Mockito.any(Pageable.class)))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/user?first_name=Sam")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.data[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.data[0].lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.data[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$.data[0].phone", is(user.getPhone())))
                .andExpect(jsonPath("$.data[0].status", is(user.getStatus().toString())));
    }

    @Test
    public void test_findUserByEmail() throws Exception {
        //create dummy user
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428119");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);

        Mockito.when(userService.getUserByEmail(Mockito.anyString()))
                .thenReturn(user);

        FindByEmailDto emailDto = new FindByEmailDto();
        emailDto.setEmail("email");

        mockMvc.perform(post("/user/one")
                .content(gson.toJson(emailDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.data.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.data.email", is(user.getEmail())))
                .andExpect(jsonPath("$.data.phone", is(user.getPhone())))
                .andExpect(jsonPath("$.data.status", is(user.getStatus().toString())));
    }

    @Test
    public void test_updateStatus() throws Exception {

        //create dummy user
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428119");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);

        Mockito.when(userService.updateStatus(Mockito.any(UpdateUserStatusDto.class)))
                .thenReturn(user);

        UpdateUserStatusDto updateUserStatusDto = new UpdateUserStatusDto();
        updateUserStatusDto.setUserId(user.getId());
        updateUserStatusDto.setStatus(user.getStatus());

        mockMvc.perform(post("/user/status")
                .content(gson.toJson(updateUserStatusDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("data.status", is(user.getStatus().toString())));
    }

    @Test
    public void test_resetPassword() throws Exception {

        // mock repo and response
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        final User user = new User();
        user.setId(200L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user.setEmail("you@gmail.com");
        user.setPhone("+233240456008");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);
        user.setCreatedBy("Reynolds");
        user.setDateCreated(new Date());
        user.setDateModified(new Date());

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setUserId(200L);
        resetPasswordDto.setOldPassword("password");
        resetPasswordDto.setNewPassword("newpassword");

        Mockito.when(userService.resetPassword(resetPasswordDto))
                .thenReturn(user);

        mockMvc.perform(post("/user/password-reset")
                .content(gson.toJson(resetPasswordDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.data.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.data.email", is(user.getEmail())))
                .andExpect(jsonPath("$.data.phone", is(user.getPhone())))
                .andExpect(jsonPath("$.data.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.data.password", is(user.getPassword())));
    }

    @Test
    public void test_forgotPassword() throws Exception {

        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Divine");
        user.setLastName("Eteba");
        user.setEmail("kofidvyn@gmail.com");
        user.setPhone("+233543308642");
        user.setPassword("password");
        user.setStatus(UserStatus.ACTIVE);


        Mockito.when(userService.forgotPassword(Mockito.any(ForgotPasswordDto.class)))
                .thenReturn(true);

        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail(user.getEmail());

        mockMvc.perform(post("/user/forgot-password")
                .content(gson.toJson(forgotPasswordDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", is((true))));
    }

    @Test
    public void test_updateUserRole() throws Exception {

        //create dummy user
        Department department = new Department();
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setName("USER");
        role.setId(200L);

        User user = new User();
        user.setId(200L);
        user.setFirstName("Mayeden");
        user.setLastName("Roy");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428119");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);

        Mockito.when(userService.updateUserRole(Mockito.any(UpdateUserRoleDto.class)))
                .thenReturn(user);

        UpdateUserRoleDto updateuserRoleDto = new UpdateUserRoleDto();
        updateuserRoleDto.setUserId(200L);
        updateuserRoleDto.setRole("USER");

        mockMvc.perform(post("/user/role")
                .content(gson.toJson(updateuserRoleDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(user.getId().intValue())));
//                .andExpect(jsonPath("data.role.name", is(user.getRole().getName())));
    }

    @Test
    public void test_sendUserInvite() throws Exception {

        Mockito.when(userService.sendUserInvite(Mockito.any(UserInviteDto.class)))
                .thenReturn(true);

        UserInviteDto userInviteDto = new UserInviteDto();
        userInviteDto.setEmail("divine@gmail.com");
        userInviteDto.setName("Joe");

        mockMvc.perform(post("/user/invite")
                .content(gson.toJson(userInviteDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data", is((true))));
    }

    @Test
    public void test_findUserById() throws Exception {
        //create dummy user
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Role role = new Role();
        role.setId(3L);
        role.setName("USER");
        role.setDateCreated(new Date());

        User user = new User();
        user.setId(200L);
        user.setFirstName("Atta");
        user.setLastName("Dwoa");
        user.setEmail("me@gmail.com");
        user.setPhone("+233241428119");
        user.setStatus(UserStatus.ACTIVE);
        user.setDepartment(department);
        user.setRole(role);

        Mockito.when(userService.getUserById(user.getId()))
                .thenReturn(user);

        mockMvc.perform(get("/user/" + user.getId())
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.data.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.data.email", is(user.getEmail())))
                .andExpect(jsonPath("$.data.phone", is(user.getPhone())))
                .andExpect(jsonPath("$.data.status", is(user.getStatus().toString())));
    }
}
