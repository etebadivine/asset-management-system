package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.dto.EditAssetDto;
import com.financemobile.fmassets.enums.AssetStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.*;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.AssetRepository;
import com.financemobile.fmassets.service.*;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public List<Asset> searchAssets(AssetSpec assetSpec, Pageable pageable) {

        List<Asset> assetList = new ArrayList<>();
        Page<Asset> assetsPage = assetRepository.findAll(assetSpec, pageable);
        if(assetsPage.hasContent())
            return assetsPage.getContent();
        return assetList;
    }

    @Override
    public Asset addAsset(CreateAssetDto createAssetDto) {
        //check if the asset already exist existByName(...)
        if(assetRepository.existsByName(createAssetDto.getName())){
            throw new AlreadyExistException("Asset already exists");
        }

        Asset asset = new Asset();
        asset.setName(createAssetDto.getName());

        //check if the location exists
        if(!StringUtils.isEmpty(createAssetDto.getLocation())){
            Location location =
                    locationService.getLocationByName(createAssetDto.getLocation());
            asset.setLocation(location);
        }

        //check if the supplier exist
        Supplier supplier = supplierService.getSupplierByName(createAssetDto.getSupplier());
        asset.setSupplier(supplier);

        //check if the department exists
        if(!StringUtils.isEmpty(createAssetDto.getDepartment())){
            Department department =
                    departmentService.getDepartmentByName(createAssetDto.getDepartment());
            asset.setDepartment(department);
        }

        AssetDetails assetDetails = new AssetDetails();
        assetDetails.setMake(createAssetDto.getMake());
        assetDetails.setColor(createAssetDto.getColor());
        assetDetails.setModel(createAssetDto.getModel());
        assetDetails.setManufacturer(createAssetDto.getManufacturer());
        assetDetails.setSerialNumber(createAssetDto.getSerialNumber());
        //check if the image is base64 encoded
        assetDetails.setImageBytes(createAssetDto.getImageBytes());
        assetDetails.setWarranty(createAssetDto.getWarranty());
        assetDetails.setLicenses(createAssetDto.getLicenses());
        asset.setAssetDetails(assetDetails);

        //check if the category exist
        Category category = categoryService.getCategoryByName(createAssetDto.getCategory());
        asset.setCategory(category);

        //check if user is passed to asset
        if(createAssetDto.getUserId() != null){
            User user = userService.getUserById(createAssetDto.getUserId());
            asset.setUser(user);
            asset.setStatus(AssetStatus.ASSIGNED);
        }
        else{
            asset.setStatus(AssetStatus.AVAILABLE);
        }
        return assetRepository.save(asset);
    }

    @Override
    public Asset editAsset(EditAssetDto editAssetDto) {

        Optional<Asset> assetOptional = assetRepository.findById(editAssetDto.getAssetId());
        if (assetOptional.isPresent()){
           Asset asset = assetOptional.get();
           asset.setName(editAssetDto.getName());

           Location location = locationService.getLocationByName(editAssetDto.getLocation());
           asset.setLocation(location);

           Supplier supplier = supplierService.getSupplierByName(editAssetDto.getSupplier());
           asset.setSupplier(supplier);

           Department department = departmentService.getDepartmentByName(editAssetDto.getDepartment());
           asset.setDepartment(department);

           Category category = categoryService.getCategoryByName(editAssetDto.getCategory());
           asset.setCategory(category);

           if (editAssetDto.getUserId() != null){
               User user = userService.getUserById(editAssetDto.getUserId());
               asset.setUser(user);
               asset.setStatus(AssetStatus.ASSIGNED);
           }
           return assetRepository.save(asset);
        }

        throw new DataNotFoundException("Asset not found");
    }
}

