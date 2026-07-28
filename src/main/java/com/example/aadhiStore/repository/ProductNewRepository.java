package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.ProductMasterNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductNewRepository extends JpaRepository<ProductMasterNew, Long> {

    @Query("""
SELECT DISTINCT p
FROM ProductMasterNew p
LEFT JOIN FETCH p.productItems pi
LEFT JOIN FETCH pi.productItemPrice
WHERE LOWER(p.productCode) LIKE LOWER(CONCAT('%', :value, '%'))
   OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :value, '%'))
""")
    List<ProductMasterNew> searchProducts(String value);

    @Query("""
        SELECT MAX(p.productCode)
        FROM ProductMasterNew p
        WHERE p.productCode LIKE CONCAT(:prefix, '-%')
    """)
    String findLastProductCode(String prefix);

//    Optional<ProductMasterNew> findByCode(String code);

    ProductMasterNew findByProductCode(String productCode);
}
