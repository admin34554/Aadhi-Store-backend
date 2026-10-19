package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CountryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryMasterRepository extends JpaRepository<CountryMaster, Long> {
}
