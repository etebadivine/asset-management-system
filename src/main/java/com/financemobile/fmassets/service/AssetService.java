package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.querySpec.AssetSpec;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AssetService {

    List<Asset> searchAssets(AssetSpec assetSpec, Pageable pageable);
    Asset addAsset(CreateAssetDto createAssetDto);

}
