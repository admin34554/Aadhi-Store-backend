package com.example.aadhiStore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReportParameterDTO {

    private String paramName;
    private String paramType;
    private String defaultValue;
    private boolean required;
}
