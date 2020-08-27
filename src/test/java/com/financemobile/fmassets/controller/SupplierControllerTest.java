package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.CreateSupplierDto;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.repository.SupplierRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SupplierControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private SupplierRepository supplierRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setup(){

    }

    @AfterEach
    public void tearDown(){

    }

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
                .contentType(MediaType.APPLICATION_JSON_VALUE))
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
}
