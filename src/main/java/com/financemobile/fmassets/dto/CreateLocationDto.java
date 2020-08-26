package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CreateLocationDto {

        @NotBlank
        private String name ;

        @NotBlank
        private String city ;

        @NotBlank
        private String country;
}


