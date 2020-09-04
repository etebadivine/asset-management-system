package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.dto.UpdateAssetStatusDto;
import com.financemobile.fmassets.dto.UpdateUserStatusDto;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.User;
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

    @GetMapping
    public ApiResponse searchAssets(AssetSpec assetSpec, Pageable pageable) {

        List<Asset> assetList = assetService.searchAssets(assetSpec, pageable);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setData(assetList);
        response.setMessage("Success");

        return response;
    }

    @PostMapping
    public ApiResponse createAsset(@RequestBody CreateAssetDto createAssetDto){
        return null;
    }


    @PostMapping("/image")
    public ApiResponse uploadImage(@RequestParam("file") MultipartFile file){
        return null;
    }

    @PostMapping(value = "/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateAssetStatus(@RequestBody UpdateAssetStatusDto updateAssetStatusDto) {

        Asset asset = assetService.updateAssetStatus(updateAssetStatusDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(asset);
        return response;
    }

}


