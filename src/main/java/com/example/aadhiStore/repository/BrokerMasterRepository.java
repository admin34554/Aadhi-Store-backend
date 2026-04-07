package com.example.aadhiStore.repository;

import com.example.aadhiStore.dto.BrokerDTO;
import com.example.aadhiStore.entity.BrokerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrokerMasterRepository extends JpaRepository<BrokerMaster, Long> {

    @Query("SELECT new com.example.aadhiStore.dto.BrokerDTO(br.id, br.brokerName, br.code) FROM BrokerMaster br")
    List<BrokerDTO> findAllForDropdown();
}
