package com.financemobile.fmassets.service;


import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssignmentHistorySpec;
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
public class AssignmentHistoryServiceTest {

    @Autowired
    private AssignmentHistoryService assignmentHistoryService;

    @Autowired
    private AssignmentHistoryRepository assignmentHistoryRepository;

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


    @Test
    public void test_trackAssetAssignment(){

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

        Asset asset = new Asset();
        asset.setName("HP Laptop");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setUser(user);
        asset = assetRepository.save(asset);

        AssignmentHistory assignmentHistory =
                assignmentHistoryService.trackAssetAssignment(asset, user);

        Assertions.assertEquals(assignmentHistory.getAsset().getId(), asset.getId());
        Assertions.assertEquals(assignmentHistory.getAsset().getName(), asset.getName());
        Assertions.assertEquals(assignmentHistory.getUser().getId(), user.getId());
        Assertions.assertEquals(assignmentHistory.getUser().getFirstName(), user.getFirstName());
    }

    @Test
    public void test_searchAssignmentHistory () {
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
        user.setEmail("reynu@gmail.com");
        user = userRepository.save(user);

        Asset asset = new Asset();
        asset.setName("HP Laptop");
        asset.setLocation(location);
        asset.setSupplier(supplier);
        asset.setDepartment(department);
        asset.setCategory(category);
        asset.setUser(user);
        asset = assetRepository.save(asset);

        AssignmentHistory assignmentHistory = new AssignmentHistory();
        assignmentHistory.setAsset(asset);
        assignmentHistory.setUser(user);
        assignmentHistoryRepository.save(assignmentHistory);

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

        AssignmentHistorySpec assignmentHistorySpec = (AssignmentHistorySpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("asset"), assignmentHistory.getAsset().getId());
            return equal;
        };

        List<AssignmentHistory> assignmentHistoryList = assignmentHistoryService.searchAssignmentHistory(assignmentHistorySpec, pageable);
        Assertions.assertEquals(assignmentHistoryList.size(), 1);
        Assertions.assertEquals(assignmentHistoryList.get(0).getAsset().getId(), assignmentHistory.getAsset().getId());
        Assertions.assertEquals(assignmentHistoryList.get(0).getUser().getId(), assignmentHistory.getUser().getId());
    }


    @Test
    public void test_searchAssets_emptyResults () {

        Sort sort = Sort.by(Sort.Direction.DESC, "dateCreated");
        Pageable pageable = PageRequest.of(0, 10, sort);

        AssignmentHistorySpec assignmentHistorySpec = (AssignmentHistorySpec) (root, query, builder) -> {
            Predicate equal =
                    builder.equal(root.get("asset"), 5L);
            return equal;
        };

        List<AssignmentHistory> assignmentHistoryList = assignmentHistoryService.searchAssignmentHistory(assignmentHistorySpec, pageable);
        Assertions.assertEquals(assignmentHistoryList.size(), 0);
    }


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
