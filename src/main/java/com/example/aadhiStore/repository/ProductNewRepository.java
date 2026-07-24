package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.ProductMasterNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductNewRepository extends JpaRepository<ProductMasterNew, Long> {

    List<ProductMasterNew> findByProductCodeContainingIgnoreCaseOrProductNameContainingIgnoreCase(String value, String value1);


    @Query("""
        SELECT MAX(p.productCode)
        FROM ProductMasterNew p
        WHERE p.productCode LIKE CONCAT(:prefix, '-%')
    """)
    String findLastProductCode(String prefix);

//    Optional<ProductMasterNew> findByCode(String code);

    ProductMasterNew findByProductCode(String productCode);
}
