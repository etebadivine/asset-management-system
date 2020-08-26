package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.AssetRepository;
import com.financemobile.fmassets.repository.DepartmentRepository;
import com.financemobile.fmassets.repository.LocationRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AssetControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AssetRepository assetRepository;

    private final Gson gson = new Gson();

    @Test
    public void test_searchAssets() throws Exception {

        Department department = new Department();
        department.setName("Finance");
        department.setCreatedBy("Manager");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());

        Location location = new Location();
        location.setCreatedBy("divine");

        Asset asset = new Asset();
        asset.setName("Laptop");
        asset.setLocation(null);
        asset.setDepartment(null);
        asset.setDepartment(department);
        asset.setLocation(location);
        Page<Asset> assetPage = new PageImpl(Arrays.asList(asset));

        Mockito.when(assetRepository.findAll(Mockito.any(AssetSpec.class), Mockito.any(Pageable.class)))
                .thenReturn(assetPage);

        mockMvc.perform(get("/asset?name=laptop")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is(asset.getName())));
    }
}