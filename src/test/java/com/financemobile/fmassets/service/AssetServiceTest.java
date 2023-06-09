package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.AlreadyExistException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.persistence.criteria.Predicate;
import java.util.List;




@SpringBootTest
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetDetailsRepository assetDetailsRepository;

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

    @Autowired
    private AssignmentHistoryRepository assignmentHistoryRepository;


    @Test
    @WithAnonymousUser
    public void test_addAsset(){
        Location location = new Location();
        location.setId(1L);
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
    @WithAnonymousUser
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
    @WithAnonymousUser
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
        editAssetDto.setAssetId(asset.getId());
        editAssetDto.setName("HP Laptop");
        editAssetDto.setLocation(location1.getName());
        editAssetDto.setSupplier(supplier1.getName());
        editAssetDto.setDepartment(department1.getName());
        editAssetDto.setCategory(category1.getName());
        editAssetDto.setUserId(user1.getId());

        Asset editedAsset = assetService.editAsset(editAssetDto);

        Assertions.assertNotNull(editedAsset.getId());
        Assertions.assertEquals(editedAsset.getName(), editAssetDto.getName());
        Assertions.assertEquals(editedAsset.getLocation().getName(), editAssetDto.getLocation());
        Assertions.assertEquals(editedAsset.getSupplier().getName(), editAssetDto.getSupplier());
        Assertions.assertEquals(editedAsset.getDepartment().getName(), editAssetDto.getDepartment());
        Assertions.assertEquals(editedAsset.getCategory().getName(), editAssetDto.getCategory());
        Assertions.assertEquals(editedAsset.getUser().getId(), editAssetDto.getUserId().intValue());
    }

    @Test
    public void test_editAsset_notFound(){
        EditAssetDto editAssetDto = new EditAssetDto();
        editAssetDto.setAssetId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.editAsset(editAssetDto);
        });
    }

    @Test
    public void test_updateAssetStatus() throws Exception {
        Department department = new Department();
        department.setName("Engineering");
        department = departmentRepository.save(department);

        Asset asset = new Asset();
        asset.setId(30L);
        asset.setName("Laptop");
        asset.setStatus(AssetStatus.DAMAGED);
        asset.setDepartment(department);
        asset = assetRepository.save(asset);

        UpdateAssetStatusDto updateAssetStatusDto =
                new UpdateAssetStatusDto();
        updateAssetStatusDto.setAssetId(asset.getId());
        updateAssetStatusDto.setAssetStatus(AssetStatus.STOLEN);

        Asset asset1 = assetService.updateAssetStatus(updateAssetStatusDto);
        Assertions.assertEquals(asset1.getStatus(),updateAssetStatusDto.getAssetStatus());
    }

    @Test
    public void test_updateAssetStatus_notFound() throws Exception {
        UpdateAssetStatusDto updateAssetStatusDto =
                new UpdateAssetStatusDto();
        updateAssetStatusDto.setAssetId(30L);
        updateAssetStatusDto.setAssetStatus(AssetStatus.DAMAGED);
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            assetService.updateAssetStatus(updateAssetStatusDto);
        });
    }

    @Test
    public void test_assignAsset(){

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

        AssignAssetDto assignAssetDto = new AssignAssetDto();
        assignAssetDto.setAssetId(asset.getId());
        assignAssetDto.setUserId(user.getId());

        AssignmentHistory assignmentHistory = new AssignmentHistory();
        assignmentHistory.setAsset(asset);
        assignmentHistory.setUser(user);

        Asset asset1 = assetService.assignAsset(assignAssetDto);
        Assertions.assertNotNull(asset1.getId());
        Assertions.assertNotNull(asset1.getUser().getId());
        Assertions.assertEquals(asset1.getId(), assignAssetDto.getAssetId());
        Assertions.assertEquals(asset1.getUser().getId(), assignAssetDto.getUserId());
        Assertions.assertEquals(asset1.getStatus(), AssetStatus.ASSIGNED);
    }

    @Test
    public void test_assignAsset_notFound(){
        AssignAssetDto assignAssetDto = new AssignAssetDto();
        assignAssetDto.setAssetId(50L);
        assignAssetDto.setUserId(60L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.assignAsset(assignAssetDto);
        });
    }

    @Test
    public void test_uploadAssetImage(){

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

        AssetDetails assetDetails = new AssetDetails();
        assetDetails.setMake("");
        assetDetails.setModel("");
        assetDetails.setColor("Black");
        assetDetails.setManufacturer("Orca Deco");
        assetDetails.setSerialNumber("3232121232132123");

        Asset asset = new Asset();
        asset.setName("HP Laptop");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setAssetDetails(assetDetails);
        asset.setUser(user);
        asset = assetRepository.save(asset);

        String imageString = "sample.jpg";
        byte[] imageBytes = imageString.getBytes();
        Asset asset1 = assetService.uploadAssetImage(asset.getId(), imageBytes);
        Assertions.assertNotNull(asset1.getId());
        Assertions.assertEquals(asset1.getAssetDetails().getImageBytes(),"c2FtcGxlLmpwZw==" );
    }

    @Test
    public void test_uploadAssetImage_notFound(){

        String imageString = "sample.jpg";
        byte[] imageByte = imageString.getBytes();

        Assertions.assertThrows(DataNotFoundException.class,()->{
            assetService.uploadAssetImage(40L, imageByte);
        });
    }


    @Test
    public void test_removeAsset() {
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

        AssetDetails assetDetails = new AssetDetails();
        assetDetails.setMake("");
        assetDetails.setModel("");
        assetDetails.setColor("Black");
        assetDetails.setManufacturer("Orca Deco");
        assetDetails.setSerialNumber("3232121232132123");

        Asset asset = new Asset();
        asset.setName("HP Laptop");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setAssetDetails(assetDetails);
        asset.setUser(user);
        asset = assetRepository.save(asset);

        assetService.removeAsset(asset.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.getAssetByName("HP Laptop");
        });
    }

//     this should always be the last method
    @AfterEach
    public void tearDown() {
        assignmentHistoryRepository.deleteAll();
        assetRepository.deleteAll();
        locationRepository.deleteAll();
        supplierRepository.deleteAll();
        departmentRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}


