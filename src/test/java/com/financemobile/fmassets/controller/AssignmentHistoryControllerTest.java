package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssignmentHistorySpec;
import com.financemobile.fmassets.repository.AssetRepository;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.AssignmentHistoryService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.Date;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentHistoryControllerTest extends OAuth2Helper {

    @MockBean
    private AssetRepository assetRepository;

    @MockBean
    private AssignmentHistoryService assignmentHistoryService;

    private final Gson gson = new Gson();

    @Test
    public void test_searchAssignmentHistory() throws Exception {

        Department department = new Department();
        department.setName("Finance");
        department.setCreatedBy("Manager");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Location location = new Location();
        location.setCreatedBy("divine");
        location.setName("Tema");

        User user = new User();
        user.setId(50L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user.setEmail("reynu@gmail.com");

        Asset asset = new Asset();
        asset.setId(40L);
        asset.setName("Laptop");
        asset.setDateCreated(new Date());
        asset.setDateModified(new Date());

        AssignmentHistory assignmentHistory = new AssignmentHistory();
        assignmentHistory.setAsset(asset);
        assignmentHistory.setUser(user);

        Mockito.when(assignmentHistoryService.searchAssignmentHistory(Mockito.any(AssignmentHistorySpec.class), Mockito.any(Pageable.class)))
                .thenReturn(Arrays.asList(assignmentHistory));

        mockMvc.perform(get("/assignment_history?asset=40")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].asset.id", is(assignmentHistory.getAsset().getId().intValue())))
                .andExpect(jsonPath("$.data[0].user.id", is(assignmentHistory.getUser().getId().intValue())))
                .andExpect(jsonPath("$.data[0].user.email", is(assignmentHistory.getUser().getEmail())));
    }
}
