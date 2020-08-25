package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.CreateSupplierDto;
import com.financemobile.fmassets.model.Supplier;


import java.util.List;

public interface SupplierService {

    Supplier addSupplier(CreateSupplierDto createSupplierDto);
    Supplier getSupplierById(Long id);
    Supplier getSupplierByName(String name);
    List<Supplier> getAllSuppliers();
    void removeSupplier(Long id);
}
