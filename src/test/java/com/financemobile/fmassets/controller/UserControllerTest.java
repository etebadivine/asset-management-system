package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.repository.UserRepository;
import com.financemobile.fmassets.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

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

        Mockito.when(userRepository.save(Mockito.any(User.class)))
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
                .contentType(MediaType.APPLICATION_JSON_VALUE))
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
        Page<User> userPage = new PageImpl(Arrays.asList(user));

        Mockito.when(userRepository.findAll(Mockito.any(UserSpec.class), Mockito.any(Pageable.class)))
                .thenReturn(userPage);

        mockMvc.perform(get("/user?first_name=Sam")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
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

        Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(user));

        FindByEmailDto emailDto = new FindByEmailDto();
        emailDto.setEmail("email");

        mockMvc.perform(post("/user/one")
                .content(gson.toJson(emailDto))
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

        Mockito.when(userRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        UpdateUserStatusDto updateUserStatusDto = new UpdateUserStatusDto();
        updateUserStatusDto.setUserId(user.getId());
        updateUserStatusDto.setStatus(user.getStatus());

        mockMvc.perform(post("/user/status")
                .content(gson.toJson(updateUserStatusDto))
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


        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setUserId(200L);
        resetPasswordDto.setOldPassword("password");
        resetPasswordDto.setNewPassword("newpassword");

        User usr = new User();
        usr.setId(200L);
        usr.setFirstName("Atta");
        usr.setLastName("Dwoa");
        usr.setEmail("me@gmail.com");
        usr.setPhone("+233241428119");
        usr.setStatus(UserStatus.ACTIVE);
        usr.setDepartment(department);
        usr.setRole(role);
        usr.setPassword(resetPasswordDto.getNewPassword());
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(usr);

        mockMvc.perform(post("/user/password-reset")
                .content(gson.toJson(resetPasswordDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data.id", is(usr.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(usr.getFirstName())))
                .andExpect(jsonPath("$.data.lastName", is(usr.getLastName())))
                .andExpect(jsonPath("$.data.email", is(usr.getEmail())))
                .andExpect(jsonPath("$.data.phone", is(usr.getPhone())))
                .andExpect(jsonPath("$.data.status", is(usr.getStatus().toString())))
                .andExpect(jsonPath("$.data.password", is(usr.getPassword())));
    }

    @Test
    public void test_resetPasswordByEmail() throws Exception {

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

        Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(user));

        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail("email");

        mockMvc.perform(post("/user/forgot-password")
                .content(gson.toJson(forgotPasswordDto))
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
        role.setName("Manager");
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

        Mockito.when(userRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        UpdateuserRoleDto updateuserRoleDto = new UpdateuserRoleDto();
        updateuserRoleDto.setUserId(user.getId());
        updateuserRoleDto.setRole(user.getRole().getName());

        mockMvc.perform(post("/user/role")
                .content(gson.toJson(updateuserRoleDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(user.getId().intValue())))
                .andExpect(jsonPath("data.role.name", is(user.getRole().getName())));
    }
}
