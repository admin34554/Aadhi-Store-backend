package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserMaster, Long> {

    Optional<UserMaster> findByUsername(String username);
}
