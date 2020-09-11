package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.enums.AssetStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAssetStatusDto {

    @NotNull
    private Long assetId;

    @NotNull
    private AssetStatus assetStatus;
}

