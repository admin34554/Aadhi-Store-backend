package com.example.aadhiStore.controller;


import com.example.aadhiStore.dto.ReportDTO;
import com.example.aadhiStore.entity.ReportMaster;
import com.example.aadhiStore.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadReport(
            @RequestParam("reportName") String reportName,
            @RequestParam("description") String description,
            @RequestParam("screenName") String screenName,
            @RequestParam("jrxmlFile") MultipartFile jrxmlFile) {

        try {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setReportName(reportName);
            reportDTO.setDescription(description);
            reportDTO.setScreenName(screenName);
            reportDTO.setJrxmlFile(jrxmlFile);

            ReportMaster savedReport = reportService.uploadReport(reportDTO);
            return ResponseEntity.ok(savedReport);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process JRXML file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        try {
            ReportMaster report = reportService.getReportById(id);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/screen/{screenName}")
    public ResponseEntity<List<ReportMaster>> getReportsByScreen(@PathVariable String screenName) {
        List<ReportMaster> reports = reportService.getReportsByScreen(screenName);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/generate/{reportId}")
    public ResponseEntity<?> generateReport(
            @PathVariable Long reportId,
            @RequestBody Map<String, Object> parameters) {

        try {
            byte[] reportContent = reportService.generateReport(reportId, parameters);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportContent);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
