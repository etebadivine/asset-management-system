package com.financemobile.fmassets.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditDepartmentDto {

        @NotNull
        private Long departmentId;
        @NotBlank
        private String name;

        public EditDepartmentDto() {
        }
}
