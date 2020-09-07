package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.CreateAssetDto;
import com.financemobile.fmassets.dto.UpdateAssetStatusDto;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.querySpec.AssetSpec;
import com.financemobile.fmassets.repository.AssetRepository;
import com.financemobile.fmassets.service.AssetService;
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
        //check if the location exist
        //check if the department exist
        //check if the supplier exist
        //check if the category exist
        // check if the image is base64 encoded
        return null;
    }

    @Override
    public Asset updateAssetStatus(UpdateAssetStatusDto updateAssetStatusDto) {
        Optional<Asset> assetOptional =
                assetRepository.findById(updateAssetStatusDto.getAssetId());

        if (assetOptional.isPresent()) {
            Asset asset = assetOptional.get();
            asset.setStatus(updateAssetStatusDto.getAssetStatus());
            return assetRepository.save(asset);
        }

        throw new DataNotFoundException("record not found");
    }
}

