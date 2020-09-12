package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class AssignAssetDto {

    @NotNull
    private Long assetId;
    @NotNull
    private Long userId;

}
