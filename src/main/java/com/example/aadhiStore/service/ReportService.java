package com.example.aadhiStore.service;


import com.example.aadhiStore.dto.ReportDTO;
import com.example.aadhiStore.entity.ReportMaster;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportService {

    ReportMaster uploadReport(ReportDTO reportDTO) throws IOException;

    byte[] generateReport(Long reportId, Map<String, Object> parameters) throws JRException, IOException;

    ReportMaster getReportById(Long id);

    List<ReportMaster> getReportsByScreen(String screenName);

}
