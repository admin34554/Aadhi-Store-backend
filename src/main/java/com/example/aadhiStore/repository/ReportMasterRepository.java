package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ReportMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportMasterRepository extends JpaRepository<ReportMaster, Long> {
    ReportMaster findByReportName(String reportName);
    List<ReportMaster> findByScreenName(String screenName);
}
