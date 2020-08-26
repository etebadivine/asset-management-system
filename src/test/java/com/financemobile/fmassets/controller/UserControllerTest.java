package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Date;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private DepartmentRepository departmentRepository;

    private final Gson gson = new Gson();

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
}
