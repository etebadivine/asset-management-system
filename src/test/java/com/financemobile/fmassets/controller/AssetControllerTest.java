package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.UpdateAssetStatusDto;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.AssetRepository;
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
        location.setName("Tema");

        Asset asset = new Asset();
        asset.setName("Laptop");
        asset.setDateCreated(new Date());
        asset.setDateModified(new Date());

        Page<Asset> assetPage = new PageImpl(Arrays.asList(asset));

        Mockito.when(assetRepository.findAll(Mockito.any(AssetSpec.class), Mockito.any(Pageable.class)))
                .thenReturn(assetPage);

        mockMvc.perform(get("/asset?name=laptop")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is(asset.getName())))
                .andExpect(jsonPath("$.data[0].department", is(asset.getDepartment())))
                .andExpect(jsonPath("$.data[0].location", is(asset.getLocation())));
    }


    @Test
    public void test_updateAssetStatus() throws Exception {

        //create dummy user
        Department department = new Department();
        department.setId(43L);
        department.setName("Engineering");
        department.setCreatedBy("Admin");
        department.setDateCreated(new Date());
        department.setDateModified(new Date());


        Asset asset = new Asset();
        asset.setName("Laptop");
        asset.setStatus(AssetStatus.DAMAGED);
        asset.setDepartment(department);

        Mockito.when(assetRepository.findByName(Mockito.any(String.class)))
                .thenReturn(Optional.of(asset));

        Mockito.when(assetRepository.save(Mockito.any(Asset.class)))
                .thenReturn(asset);

        UpdateAssetStatusDto updateAssetStatusDto = new UpdateAssetStatusDto();
        updateAssetStatusDto.setAssetName(asset.getName());
        updateAssetStatusDto.setAssetStatus(asset.getStatus());

        mockMvc.perform(post("/asset/status")
                .content(gson.toJson(updateAssetStatusDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.name", is(asset.getName())))
                .andExpect(jsonPath("data.status", is(asset.getStatus().toString())));
    }
}