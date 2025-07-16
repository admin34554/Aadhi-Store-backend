package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.ReportDTO;
import com.example.aadhiStore.entity.ReportMaster;
import com.example.aadhiStore.repository.ReportMasterRepository;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{

    private final ReportMasterRepository reportRepository;

    public ReportServiceImpl(ReportMasterRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportMaster uploadReport(ReportDTO reportDTO) throws IOException {
        MultipartFile jrxmlFile = reportDTO.getJrxmlFile();
        validateJrxmlFile(jrxmlFile);

        ReportMaster report = new ReportMaster();
        report.setReportName(reportDTO.getReportName());
        report.setDescription(reportDTO.getDescription());
        report.setScreenName(reportDTO.getScreenName());
        report.setJrxmlFile(jrxmlFile.getBytes());
        report.setReportFile(jrxmlFile.getBytes());
        report.setCreatedDate(new Date());
        report.setModifiedDate(new Date());

        return reportRepository.save(report);
    }

    @Override
    public byte[] generateReport(Long reportId, Map<String, Object> parameters) throws JRException, IOException {
        ReportMaster report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

        try (ByteArrayInputStream jrxmlStream = new ByteArrayInputStream(report.getJrxmlFile())) {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    @Override
    public ReportMaster getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    }

    @Override
    public List<ReportMaster> getReportsByScreen(String screenName) {
        return reportRepository.findByScreenName(screenName);
    }

    private void validateJrxmlFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("JRXML file is required");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!fileName.toLowerCase().endsWith(".jrxml")) {
            throw new IllegalArgumentException("Invalid file type. Expected .jrxml");
        }

        // Basic content validation
        String contentStart = new String(file.getBytes(), 0, Math.min(100, file.getBytes().length));
        if (!contentStart.contains("<?xml") && !contentStart.contains("<jasperReport")) {
            throw new IllegalArgumentException("Invalid JRXML content format");
        }
    }
}
