package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.CreateLocationDto;
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
}

