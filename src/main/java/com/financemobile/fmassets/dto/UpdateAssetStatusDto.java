package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.enums.AssetStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateAssetStatusDto {

    @NotBlank
    private String assetName;

    @NotBlank
    private AssetStatus assetStatus;
}

