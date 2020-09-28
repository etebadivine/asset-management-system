package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.repository.SupplierRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.List;

@SpringBootTest
public class SupplierServiceTest {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    @WithAnonymousUser
    public void test_addSupplier(){
        String name = "Orca Home";
        String address = "Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        Assertions.assertNotNull(supplier.getId());
        Assertions.assertEquals(supplier.getName(), name);
        Assertions.assertEquals(supplier.getAddress(), address);
        Assertions.assertEquals(supplier.getTelephone(), telephone);
        Assertions.assertEquals(supplier.getMobile(), mobile);
    }

    @Test
    public void test_addSupplier_duplicate(){
        String name = "Orca Home";
        String address = "Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        supplierService.addSupplier(createSupplierDto);

        Assertions.assertThrows(AlreadyExistException.class, ()->{
           supplierService.addSupplier(createSupplierDto);
        });
    }

    @Test
    public void test_getSupplierById(){
        String name = "Orca Home";
        String address = " Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        Supplier suplr = supplierService.getSupplierById(supplier.getId());
        Assertions.assertEquals(supplier.getId(), suplr.getId());
        Assertions.assertEquals(supplier.getName(), name);
        Assertions.assertEquals(supplier.getAddress(), address);
        Assertions.assertEquals(supplier.getTelephone(), telephone);
        Assertions.assertEquals(supplier.getMobile(), mobile);
    }

    @Test
    public void test_getSupplierById_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            supplierService.getSupplierById(4L);
        });
    }

    @Test
    public void test_getSupplierByName(){
        String name = "Orca Home";
        String address = " Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        Supplier suplr = supplierService.getSupplierByName(name);
        Assertions.assertEquals(supplier.getId(), suplr.getId());
        Assertions.assertEquals(supplier.getName(), suplr.getName());
        Assertions.assertEquals(supplier.getAddress(), suplr.getAddress());
        Assertions.assertEquals(supplier.getTelephone(), suplr.getTelephone());
        Assertions.assertEquals(supplier.getMobile(), suplr.getMobile());
    }

    @Test
    public void test_getSupplierByName_notFound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            supplierService.getSupplierByName("Kantaka");
        });
    }

    @Test
    public void test_getAllSuppliers(){
        String name = "Orca Home";
        String address = " Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        List<Supplier> suplrList = supplierService.getAllSuppliers();
        Assertions.assertEquals(suplrList.size(), 1);
        Assertions.assertEquals(suplrList.get(0).getName(), name);
        Assertions.assertEquals(suplrList.get(0).getAddress(), address);
        Assertions.assertEquals(suplrList.get(0).getTelephone(), telephone);
        Assertions.assertEquals(suplrList.get(0).getMobile(), mobile);
    }

    @Test
    public void test_editSupplier() {
        String name = "Orca Home";
        String address = "Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        EditSupplierDto editSupplierDto = new EditSupplierDto();
        editSupplierDto.setSupplierId(supplier.getId());
        editSupplierDto.setName("Orca Deco");
        editSupplierDto.setAddress("Tema");
        editSupplierDto.setTelephone("+0302151515656");
        editSupplierDto.setMobile("0201545484");

        Supplier editedSupplier= supplierService.editSupplier(editSupplierDto);

        Assertions.assertNotNull(editedSupplier.getId());
        Assertions.assertEquals(editedSupplier.getName(), editSupplierDto.getName());
        Assertions.assertEquals(editedSupplier.getAddress(), editSupplierDto.getAddress());
        Assertions.assertEquals(editedSupplier.getTelephone(), editSupplierDto.getTelephone());
        Assertions.assertEquals(editedSupplier.getMobile(), editSupplierDto.getMobile());
    }

    @Test
    public void test_editSupplier_notFound(){
        EditSupplierDto editSupplierDto = new EditSupplierDto();
        editSupplierDto.setSupplierId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            supplierService.editSupplier(editSupplierDto);
        });
    }

    @Test
    public void test_removeSupplier() {
        String name = "Orca Home";
        String address = " Accra";
        String telephone = "+211 24 333 9999";
        String mobile = "+233 54 214 878";
        CreateSupplierDto createSupplierDto = new CreateSupplierDto(name, address, telephone, mobile);
        Supplier supplier =  supplierService.addSupplier(createSupplierDto);

        supplierService.removeSupplier(supplier.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            supplierService.getSupplierByName(name);
        });
    }

    @AfterEach
    public  void tearDown() {
        supplierRepository.deleteAll();
    }
}
