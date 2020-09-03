package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
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

    @BeforeEach
    public void setup() {
//        assetRepository.deleteAll();
//        departmentRepository.deleteAll();
//        locationRepository.deleteAll();
    }

//    @AfterEach
//    public void tearDown() {
//        assetRepository.deleteAll();
////        departmentRepository.deleteAll();
////        locationRepository.deleteAll();
//    }

    @Test
    public void test_addAsset(){
        String name = "Executive Desk";
        String location = "Comm 8";
        String supplier = "Orca Home";
        String department = "Human Resource";
        String category = "Furniture";
        CreateAssetDto createAssetDto = new CreateAssetDto(name,location,supplier,department,category);

        Asset asset = assetService.addAsset(createAssetDto);
        Assertions.assertNotNull(asset.getId());
        Assertions.assertEquals(asset.getName(), name);
        Assertions.assertEquals(asset.getLocation().getName(),location);
        Assertions.assertEquals(asset.getSupplier().getName(), supplier);
        Assertions.assertEquals(asset.getDepartment().getName(), department);
        Assertions.assertEquals(asset.getCategory().getName(), category);
    }

    @Test
    public void test_addAsset_duplicate(){
        String name = "Executive Desk";
        String location = "Comm 8";
        String supplier = "Orca Home";
        String department = "Human Resource";
        String category = "Furniture";
        CreateAssetDto createAssetDto = new CreateAssetDto(name,location,supplier,department,category);

        Asset asset = assetService.addAsset(createAssetDto);
        Assertions.assertThrows(AlreadyExistException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }

    @Test
    public void test_addAsset_LocationNotFound(){
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setLocation("Kasoa");
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }

    @Test
    public void test_addAsset_SupplierNotFound(){
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setSupplier("AshFoam");
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }

    @Test
    public void test_addAsset_DepartmentNotFound(){
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setLocation("MakeUp");
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }

    @Test
    public void test_addAsset_CategoryNotFound(){
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setCategory("Food");
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
    }

    @Test
    public void test_addAsset_UserNotFound(){
        CreateAssetDto createAssetDto = new CreateAssetDto();
        createAssetDto.setUserId(300L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            assetService.addAsset(createAssetDto);
        });
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
}


