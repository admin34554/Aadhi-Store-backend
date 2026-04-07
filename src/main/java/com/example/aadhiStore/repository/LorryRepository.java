package com.example.aadhiStore.repository;

import com.example.aadhiStore.dto.LorryDTO;
import com.example.aadhiStore.entity.LorryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LorryRepository extends JpaRepository<LorryMaster, Long> {
    @Query("SELECT LorryDTO(l.id, l.name) FROM LorryMaster l")
    List<LorryDTO> findAllForDropdown();}
