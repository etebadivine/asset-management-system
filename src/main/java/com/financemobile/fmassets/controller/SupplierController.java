package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateSupplierDto;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/supplier")
@RestController
public class SupplierController {

    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createSupplier(@RequestBody CreateSupplierDto createSupplierDto){

        Supplier supplier = supplierService.addSupplier(
               createSupplierDto
                );


        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(supplier);
        return response;
    }

    @GetMapping
    public ApiResponse findAll(){
    ApiResponse response = new ApiResponse();
    response.setStatus(true);
    response.setMessage("Success");
    response.setData(supplierService.getAllSuppliers());
    return  response;
    }
}
