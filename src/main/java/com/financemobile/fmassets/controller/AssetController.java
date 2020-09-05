package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequestMapping("/asset")
@RestController
public class AssetController {

    private AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createAsset(@RequestBody CreateAssetDto createAssetDto){

        Asset asset = assetService.addAsset(createAssetDto);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(asset);
        return response;
    }

    @GetMapping
    public ApiResponse searchAssets(AssetSpec assetSpec, Pageable pageable) {

        List<Asset> assetList = assetService.searchAssets(assetSpec, pageable);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setData(assetList);
        response.setMessage("Success");

        return response;
    }

    @PostMapping("/image")
    public ApiResponse uploadImage(@RequestParam("file") MultipartFile file){
        return null;
    }

}


