package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/asset")
@RestController
public class AssetController {

    private AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ApiResponse searchAssets(AssetSpec assetSpec, Pageable pageable) {

        List<Asset> assetList = assetService.searchAssets(assetSpec, pageable);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setData(assetList);
        response.setMessage("Success");


        ApiResponse getAssetByName;
        Asset asset = assetService.getAssetByName("laptop");

        ApiResponse getAssetByLocation;
        Asset asset1 = assetService.getAssetByLocation("Tema");

        ApiResponse getAssetByDepartment;
        Asset asset2 = assetService.getAssetByDepartment("finance");

        return response;
    }
}


