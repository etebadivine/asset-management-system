package com.financemobile.fmassets.service;


import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        AssignmentHistory assignmentHistory =
                assignmentHistoryService.trackAssetAssignment(asset, user);

        Assertions.assertEquals(assignmentHistory.getAsset().getId(), asset.getId());
        Assertions.assertEquals(assignmentHistory.getAsset().getName(), asset.getName());
        Assertions.assertEquals(assignmentHistory.getUser().getId(), user.getId());
        Assertions.assertEquals(assignmentHistory.getUser().getFirstName(), user.getFirstName());
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
