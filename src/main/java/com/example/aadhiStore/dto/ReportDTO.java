package com.example.aadhiStore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Data
public class ReportDTO {
    private String reportName;
    private String description;
    private String screenName;
    private MultipartFile jrxmlFile;
}
