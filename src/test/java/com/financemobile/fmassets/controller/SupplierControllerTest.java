package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.CreateSupplierDto;
import com.financemobile.fmassets.dto.EditAssetDto;
import com.financemobile.fmassets.dto.EditSupplierDto;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.repository.SupplierRepository;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.SupplierService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SupplierControllerTest extends OAuth2Helper {

    @MockBean
    private SupplierRepository supplierRepository;

    @MockBean
    private SupplierService supplierService;

    private final Gson gson = new Gson();

    @Test
    public void test_addSupplier() throws Exception{

        // mock repo and response
        Supplier supplier = new Supplier();
        supplier.setId(3L);
        supplier.setName("Kantanka");
        supplier.setAddress("Acrra");
        supplier.setTelephone("+211 54 222 9887");
        supplier.setMobile("+233 54 259 5566");
        supplier.setCreatedBy("Reynolds");
        supplier.setDateCreated(new Date());
        supplier.setDateModified(new Date());

        Mockito.when(supplierRepository.save(Mockito.any(Supplier.class)))
                .thenReturn(supplier);

        // payload for the endpoint
        CreateSupplierDto createSupplierDto = new CreateSupplierDto();
        createSupplierDto.setName(supplier.getName());
        createSupplierDto.setAddress(supplier.getAddress());
        createSupplierDto.setTelephone(supplier.getTelephone());
        createSupplierDto.setMobile(supplier.getMobile());

        // fire request
        mockMvc.perform(post("/supplier")
                .content(gson.toJson(createSupplierDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(supplier.getId().intValue())))
                .andExpect(jsonPath("data.name", is(supplier.getName())))
                .andExpect(jsonPath("data.address", is(supplier.getAddress())))
                .andExpect((jsonPath("data.telephone", is(supplier.getTelephone()))))
                .andExpect((jsonPath("data.mobile", is(supplier.getMobile()))));
    }

    @Test
    public void test_getAllSuppliers() throws Exception {

        // mock repo and response
        Supplier supplier = new Supplier();
        supplier.setId(3L);
        supplier.setName("Kantanka");
        supplier.setAddress("Acrra");
        supplier.setTelephone("+211 54 222 9887");
        supplier.setMobile("+233 54 259 5566");
        supplier.setCreatedBy("Reynolds");
        supplier.setDateCreated(new Date());
        supplier.setDateModified(new Date());

        List<Supplier> supplierList = Arrays.asList(supplier);

        Mockito.when(supplierRepository.findAll())
                .thenReturn(supplierList);

        // fire request
        mockMvc.perform(get("/supplier")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(supplier.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(supplier.getName())))
                .andExpect(jsonPath("$.data[0].address", is(supplier.getAddress())))
                .andExpect(jsonPath("$.data[0].telephone", is(supplier.getTelephone())))
                .andExpect(jsonPath("$.data[0].mobile", is(supplier.getMobile())));
    }

    @Test
    public void test_editSupplier() throws Exception {

        Supplier supplier = new Supplier();
        supplier.setId(300L);
        supplier.setName("Franko Tranding Enterplise");
        supplier.setAddress("Accra");
        supplier.setTelephone("+211 24 333 9999");
        supplier.setMobile("+233 54 214 878");
        supplier.setCreatedBy("Reynolds");
        supplier.setDateCreated(new Date());
        supplier.setDateModified(new Date());

        Mockito.when(supplierService.editSupplier(Mockito.any(EditSupplierDto.class)))
                .thenReturn(supplier);

        EditSupplierDto editSupplierDto = new EditSupplierDto();
        editSupplierDto.setSupplierId(supplier.getId());
        editSupplierDto.setName("Franko Trading Enterprise");
        editSupplierDto.setAddress("Accra");
        editSupplierDto.setTelephone("+211 24 333 9999");
        editSupplierDto.setMobile("+233 50 215 878");


        mockMvc.perform(put("/supplier")
                .content(gson.toJson(editSupplierDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(supplier.getId().intValue())))
                .andExpect(jsonPath("data.name", is(supplier.getName())))
                .andExpect(jsonPath("data.address", is(supplier.getAddress())))
                .andExpect(jsonPath("data.telephone", is(supplier.getTelephone())))
                .andExpect(jsonPath("data.mobile", is(supplier.getMobile())));
    }

    @Test
    public void test_removeSupplier() throws Exception {

        Supplier supplier = new Supplier();
        supplier.setId(300L);
        supplier.setName("Franko Tranding Enterplise");
        supplier.setAddress("Accra");
        supplier.setTelephone("+211 24 333 9999");
        supplier.setMobile("+233 54 214 878");
        supplier.setCreatedBy("Reynolds");
        supplier.setDateCreated(new Date());
        supplier.setDateModified(new Date());

        supplierService.removeSupplier(Mockito.any(Long.class));

        mockMvc.perform(delete("/supplier/" + supplier.getId())
                .content(gson.toJson(supplier))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data", is("Deleted")));
    }
}
