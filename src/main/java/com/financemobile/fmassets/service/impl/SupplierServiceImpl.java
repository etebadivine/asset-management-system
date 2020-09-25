package com.financemobile.fmassets.service.impl;

import ch.qos.logback.classic.Logger;
import com.financemobile.fmassets.dto.CreateSupplierDto;
import com.financemobile.fmassets.dto.EditSupplierDto;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.repository.SupplierRepository;
import com.financemobile.fmassets.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier addSupplier(CreateSupplierDto createSupplierDto) {

        if(supplierRepository.existsByName(createSupplierDto.getName()))
                throw new AlreadyExistException("record already exists");

        Supplier supplier = new Supplier();
        supplier.setName(createSupplierDto.getName());
        supplier.setAddress(createSupplierDto.getAddress());
        supplier.setTelephone(createSupplierDto.getTelephone());
        supplier.setMobile(createSupplierDto.getMobile());

        return supplierRepository.save(supplier);
    }


    @Override
    public Supplier getSupplierById(Long id) throws DataNotFoundException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);

        if(supplierOptional.isPresent()){
            return supplierOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public Supplier getSupplierByName(String name) {
        Optional<Supplier> supplierOptional =
                supplierRepository.findByName(name);

        if(supplierOptional.isPresent()){
            return supplierOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier editSupplier(EditSupplierDto editSupplierDto) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(editSupplierDto.getSupplierId());
        if (supplierOptional.isPresent()){
            Supplier supplier = supplierOptional.get();
            supplier.setName(editSupplierDto.getName());
            supplier.setAddress(editSupplierDto.getAddress());
            supplier.setTelephone(editSupplierDto.getTelephone());
            supplier.setMobile(editSupplierDto.getMobile());

            return supplierRepository.save(supplier);
        }
        throw new DataNotFoundException("Supplier not found");
    }

    @Override
    public void removeSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}

