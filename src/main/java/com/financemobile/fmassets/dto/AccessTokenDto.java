package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.model.User;
import lombok.Data;

@Data
public class AccessTokenDto {

    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;
    private User user;
}
