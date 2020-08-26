package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

    @AfterEach
    public void tearDown() {
        assetRepository.deleteAll();
        departmentRepository.deleteAll();
        locationRepository.deleteAll();
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
            asset.setLocation(location);
            asset.setDepartment(department);
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
    }
}


