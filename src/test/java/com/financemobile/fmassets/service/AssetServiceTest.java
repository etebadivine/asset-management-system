package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.dto.EditAssetDto;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.ImageFormatException;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;
import java.util.List;


@SpringBootTest
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
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
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
           assetRepository.deleteAll();
           locationRepository.deleteAll();
           supplierRepository.deleteAll();
           departmentRepository.deleteAll();
           categoryRepository.deleteAll();

    }

      @AfterEach
       public void tearDown() {
           assetRepository.deleteAll();
           locationRepository.deleteAll();
           supplierRepository.deleteAll();
           departmentRepository.deleteAll();
           categoryRepository.deleteAll();
    }

    @Test
    public void test_addAsset(){
        Location location = new Location();
        location.setId(1l);
        location.setName("Tema");
        location = locationRepository.save(location);

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setName("Orca Home");
        supplier = supplierRepository.save(supplier);

        Department department = new Department();
        department.setId(3L);
        department.setName("Human Resource");
        department = departmentRepository.save(department);

        Category category = new Category();
        category.setId(4L);
        category.setName("Furniture");
        category = categoryRepository.save(category);

        User user = new User();
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user = userRepository.save(user);


        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setName("Executive Desk");
        createAssetDto.setLocation(location.getName());
        createAssetDto.setSupplier(supplier.getName());
        createAssetDto.setDepartment(department.getName());
        createAssetDto.setMake("");
        createAssetDto.setColor("white");
        createAssetDto.setModel("2C7");
        createAssetDto.setCategory(category.getName());
        createAssetDto.setManufacturer("Orca Home");
        createAssetDto.setSerialNumber("65465465465456564");
        createAssetDto.setImageBytes("dHJ5dGZ5dGZ5dGZ5dA==");
        createAssetDto.setWarranty("One Year");
        createAssetDto.setUserId(user.getId());


        Asset asset = assetService.addAsset(createAssetDto);
        Assertions.assertNotNull(asset.getId());
        Assertions.assertEquals(asset.getName(), createAssetDto.getName());
        Assertions.assertEquals(asset.getLocation().getName(), createAssetDto.getLocation());
        Assertions.assertEquals(asset.getSupplier().getName(), createAssetDto.getSupplier());
        Assertions.assertEquals(asset.getDepartment().getName(), createAssetDto.getDepartment());
        Assertions.assertEquals(asset.getCategory().getName(), createAssetDto.getCategory());
        Assertions.assertEquals(asset.getUser().getId(), createAssetDto.getUserId().intValue());
        Assertions.assertEquals(asset.getStatus(), AssetStatus.ASSIGNED);
    }

    @Test
    public void test_addAsset_duplicate(){

        Category category = new Category();
        category.setId(4L);
        category.setName("Furniture");
        category = categoryRepository.save(category);

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setName("Orca Home");
        supplier = supplierRepository.save(supplier);


        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setName("Executive Desk");
        createAssetDto.setSupplier(supplier.getName());
        createAssetDto.setMake("");
        createAssetDto.setColor("white");
        createAssetDto.setModel("2C7");
        createAssetDto.setCategory(category.getName());
        createAssetDto.setManufacturer("Orca Home");
        createAssetDto.setSerialNumber("65465465465456564");
        createAssetDto.setImageBytes("dHJ5dGZ5dGZ5dGZ5dA==");
        createAssetDto.setWarranty("One Year");


        assetService.addAsset(createAssetDto);
        Assertions.assertThrows(AlreadyExistException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }


    @Test
    public void test_assetNotAssigned(){

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setName("Orca Home");
        supplier = supplierRepository.save(supplier);

        Category category = new Category();
        category.setId(4L);
        category.setName("Furniture");
        category = categoryRepository.save(category);

        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setName("Executive Desk");
        createAssetDto.setSupplier(supplier.getName());
        createAssetDto.setMake("");
        createAssetDto.setColor("white");
        createAssetDto.setModel("2C7");
        createAssetDto.setCategory(category.getName());
        createAssetDto.setManufacturer("Orca Home");
        createAssetDto.setSerialNumber("65465465465456564");
        createAssetDto.setImageBytes("dHJ5dGZ5dGZ5dGZ5dA==");
        createAssetDto.setWarranty("One Year");

        Asset asset = assetService.addAsset(createAssetDto);
        Assertions.assertNotNull(asset.getId());
        Assertions.assertEquals(asset.getName(), createAssetDto.getName());
        Assertions.assertEquals(asset.getSupplier().getName(), createAssetDto.getSupplier());
        Assertions.assertEquals(asset.getCategory().getName(), createAssetDto.getCategory());
        Assertions.assertEquals(asset.getStatus(), AssetStatus.AVAILABLE);
    }


    @Test
    public void test_searchAssets () {
        Department department = new Department();
        department.setName("Finance");
        department = departmentRepository.save(department);

        Location location = new Location();
        location.setName("Tema");
        location.setCreatedBy("divine");
        location = locationRepository.save(location);

        Asset asset = new Asset();
        asset.setName("Laptop");
        asset.getAssetDetails();
        asset.getDateCreated();
        assetRepository.save(asset);

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

        AssetSpec assetSpec = (AssetSpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("name"), asset.getName());
            return equal;
        };

        List<Asset> assetList = assetService.searchAssets(assetSpec, pageable);
        Assertions.assertEquals(assetList.size(), 1);
        Assertions.assertEquals(assetList.get(0).getName(), asset.getName());
        Assertions.assertEquals(assetList.get(0).getLocation(), asset.getLocation());
        Assertions.assertEquals(assetList.get(0).getDepartment(), asset.getDepartment());
    }

    @Test
    public void test_searchAssets_emptyResults () {

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

        AssetSpec assetSpec = (AssetSpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("name"), "non_existent_asset");
            return equal;
        };

        List<Asset> assetList = assetService.searchAssets(assetSpec, pageable);
        Assertions.assertEquals(assetList.size(), 0);
    }

    @Test
    public void test_editAsset(){

        Location location = new Location();
        location.setId(1l);
        location.setName("Tema");
        location = locationRepository.save(location);

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setName("Orca Home");
        supplier = supplierRepository.save(supplier);

        Department department = new Department();
        department.setId(3L);
        department.setName("Human Resource");
        department = departmentRepository.save(department);

        Category category = new Category();
        category.setId(4L);
        category.setName("Furniture");
        category = categoryRepository.save(category);

        User user = new User();
        user.setFirstName("Reynolds");
        user.setLastName("Adanu");
        user = userRepository.save(user);

        Asset asset = new Asset();
        asset.setName("HP Laptop");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setUser(user);
        asset = assetRepository.save(asset);


        Location location1 = new Location();
        location1.setName("Kumasi");
        location1 = locationRepository.save(location1);

        Supplier supplier1 = new Supplier();
        supplier1.setName("Franko Trading Enterprise");
        supplier1 = supplierRepository.save(supplier1);

        Department department1 = new Department();
        department1.setName("Finance");
        department1 = departmentRepository.save(department1);

        Category category1 = new Category();
        category1.setName("Computers");
        category1 = categoryRepository.save(category1);

        User user1 = new User();
        user1.setFirstName("Kwasi");
        user1.setLastName("Adanu");
        user1 = userRepository.save(user1);

        EditAssetDto editAssetDto = new EditAssetDto();
        editAssetDto.setName(asset.getName());
        editAssetDto.setLocation(location1.getName());
        editAssetDto.setSupplier(supplier1.getName());
        editAssetDto.setDepartment(department1.getName());
        editAssetDto.setCategory(category1.getName());
        editAssetDto.setUserId(user1.getId());

        assetService.editAsset(editAssetDto);
    }
}


