package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateLocationDto {

        @NotBlank
        private String name;
        private String city;
        private String country;
}


