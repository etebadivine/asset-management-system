package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.*;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired


    private final Gson gson = new Gson();

    @Test
    public void test_addAsset() throws Exception{
        //mock repo and response
        Location location = new Location();
        location.setName("Tema");
        location.setCreatedBy("divine");
        location = locationRepository.save(location);

        Supplier supplier = new Supplier();
        supplier.setName("AshForm");
        supplier.setAddress("Accra");
        supplier.setTelephone("+211 24 333 9999");
        supplier.setMobile("+233 54 214 878");
        supplier = supplierRepository.save(supplier);

        Department department = new Department();
        department.setName("Kitchen");
        department = departmentRepository.save(department);

        Category category = new Category();
        category.setName("Biscuits");
        category.setDescription("Fried,Baked");
        category = categoryRepository.save(category);

        Asset asset = new Asset();
        asset.setName("Digestive");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setCreatedBy("Reynolds");
        asset.setDateCreated(new Date());
        asset.setDateModified(new Date());

        Mockito.when(assetRepository.save(Mockito.any(Asset.class)))
                .thenReturn(asset);

        //payload for the endpoint
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setName(asset.getName());
        createAssetDto.setLocation(asset.getLocation().getName());
        createAssetDto.setSupplier(asset.getSupplier().getName());
        createAssetDto.setDepartment(asset.getDepartment().getName());
        createAssetDto.setCategory(asset.getCategory().getName());

        //fire request
        mockMvc.perform(post("/asset")
                .content(gson.toJson(createAssetDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
//                .andExpect(jsonPath("data.id", is(asset.getId().intValue())))
                .andExpect(jsonPath("data.name", is(asset.getName())))
                .andExpect(jsonPath("data.location", is(asset.getLocation())))
                .andExpect(jsonPath("data.supplier", is(asset.getSupplier())))
                .andExpect(jsonPath("data.department", is(asset.getDepartment())))
                .andExpect(jsonPath("data.Category", is(asset.getCategory())));
    }

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
}