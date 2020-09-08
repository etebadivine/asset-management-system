package com.financemobile.fmassets.service;


import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssignmentHistoryServiceTest {

    @Autowired
    private AssignmentHistoryService assignHistServ;

    @Autowired
    private AssignmentHistoryRepository assignHistRepo;

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

        AssignmentHistory assignmentHistory = new AssignmentHistory();
        assignmentHistory.setAsset(asset);
        assignmentHistory.setUser(user);
        assignmentHistory = assignHistRepo.save(assignmentHistory);

        AssignmentHistory assignmentHistory1 = assignHistServ.trackAssetAssignment(assignmentHistory.getAsset(), assignmentHistory.getUser());
        Assertions.assertNotNull(assignmentHistory1.getAsset().getId());
        Assertions.assertNotNull(assignmentHistory1.getUser().getId());
        Assertions.assertEquals(assignmentHistory1.getAsset().getId(), assignmentHistory.getAsset().getId());
        Assertions.assertEquals(assignmentHistory1.getUser().getId(), assignmentHistory.getUser().getId());
    }
}
