package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.querySpec.AssetSpec;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AssetService {

    List<Asset> searchAssets(AssetSpec assetSpec, Pageable pageable);
    Asset addAsset(CreateAssetDto createAssetDto);
    Asset editAsset(EditAssetDto editAssetDto);
    Asset updateAssetStatus(UpdateAssetStatusDto updateAssetStatusDto);
    Asset assignAsset(AssignAssetDto assignAssetDto);
    Asset uploadAssetImage(Long assetId, byte[] imagebytes);
    void removeAsset(Long id);
}
