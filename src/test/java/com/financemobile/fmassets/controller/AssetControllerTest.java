package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.dto.EditAssetDto;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.*;
import com.financemobile.fmassets.service.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AssetControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AssetRepository assetRepository;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private SupplierService supplierService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    private final Gson gson = new Gson();

    @Test
    public void test_addAsset() throws Exception{
        //mock repo and response
        Location location = new Location();
        location.setName("Tema");
        location.setCreatedBy("divine");

        Supplier supplier = new Supplier();
        supplier.setName("Mockito's Bakery");
        supplier.setAddress("Accra");
        supplier.setTelephone("+211 24 333 9999");
        supplier.setMobile("+233 54 214 878");

        Department department = new Department();
        department.setName("Kitchen");

        Category category = new Category();
        category.setName("Biscuits");
        category.setDescription("Fried,Baked");

        User user = new User();
        user.setId(20L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");

        Asset asset = new Asset();
        asset.setId(300L);
        asset.setName("Digestive");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setUser(user);
        asset.setCreatedBy("Reynolds");
        asset.setDateCreated(new Date());
        asset.setDateModified(new Date());

        Mockito.when(locationService.getLocationByName(Mockito.anyString()))
                .thenReturn(location);
        Mockito.when(supplierService.getSupplierByName(Mockito.anyString()))
                .thenReturn(supplier);
        Mockito.when(departmentService.getDepartmentByName(Mockito.anyString()))
                .thenReturn(department);
        Mockito.when(categoryService.getCategoryByName(Mockito.anyString()))
                .thenReturn(category);
        Mockito.when(userService.getUserById(Mockito.anyLong()))
                .thenReturn(user);

        Mockito.when(assetRepository.save(Mockito.any(Asset.class)))
                .thenReturn(asset);

        //payload for the endpoint
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setName("Laptop");
        createAssetDto.setLocation("Tema");
        createAssetDto.setSupplier("Kantanka");
        createAssetDto.setDepartment("Engineering");
        createAssetDto.setCategory("Computers");
        createAssetDto.setUserId(200L);

        //fire request
        mockMvc.perform(post("/asset")
                .content(gson.toJson(createAssetDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(asset.getId().intValue())))
                .andExpect(jsonPath("data.name", is(asset.getName())))
                .andExpect(jsonPath("data.location.name", is(asset.getLocation().getName())))
                .andExpect(jsonPath("data.supplier.name", is(asset.getSupplier().getName())))
                .andExpect(jsonPath("data.department.name", is(asset.getDepartment().getName())))
                .andExpect(jsonPath("data.category.name", is(asset.getCategory().getName())))
                .andExpect(jsonPath("data.user.id",is(asset.getUser().getId().intValue())));
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

    @Test
    public void test_editAsset() throws Exception {

        Location location = new Location();
        location.setName("Tema");
        location.setCreatedBy("divine");

        Supplier supplier = new Supplier();
        supplier.setName("Mockito's Bakery");
        supplier.setAddress("Accra");
        supplier.setTelephone("+211 24 333 9999");
        supplier.setMobile("+233 54 214 878");

        Department department = new Department();
        department.setName("Kitchen");

        Category category = new Category();
        category.setName("Biscuits");
        category.setDescription("Food");

        User user = new User();
        user.setId(20L);
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");

        Asset asset = new Asset();
        asset.setId(300L);
        asset.setName("Digestive");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setUser(user);
        asset.setCreatedBy("Reynolds");
        asset.setDateCreated(new Date());
        asset.setDateModified(new Date());

        Mockito.when(assetRepository.findByName(asset.getName()))
                .thenReturn(Optional.of(asset));

        Mockito.when(locationService.getLocationByName(Mockito.anyString()))
                .thenReturn(location);
        Mockito.when(supplierService.getSupplierByName(Mockito.anyString()))
                .thenReturn(supplier);
        Mockito.when(departmentService.getDepartmentByName(Mockito.anyString()))
                .thenReturn(department);
        Mockito.when(categoryService.getCategoryByName(Mockito.anyString()))
                .thenReturn(category);
        Mockito.when(userService.getUserById(Mockito.anyLong()))
                .thenReturn(user);

        Mockito.when(assetRepository.save(Mockito.any(Asset.class)))
                .thenReturn(asset);

        EditAssetDto editAssetDto = new EditAssetDto();
        editAssetDto.setName(asset.getName());
        editAssetDto.setLocation("Accra");
        editAssetDto.setSupplier("Spring's Bakery");
        editAssetDto.setDepartment("Kitchen");
        editAssetDto.setCategory("Food");
        editAssetDto.setUserId(user.getId());

        mockMvc.perform(post("/asset/edit-asset")
                .content(gson.toJson(editAssetDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(asset.getId().intValue())))
                .andExpect(jsonPath("data.location.name", is(asset.getLocation().getName())))
                .andExpect(jsonPath("data.supplier.name", is(asset.getSupplier().getName())))
                .andExpect(jsonPath("data.department.name", is(asset.getDepartment().getName())))
                .andExpect(jsonPath("data.category.name", is(asset.getCategory().getName())))
                .andExpect(jsonPath("data.user.id", is(asset.getUser().getId().intValue())));
    }
}