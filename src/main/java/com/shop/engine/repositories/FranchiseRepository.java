package com.shop.engine.repositories;

import com.shop.engine.models.FranchiseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranchiseRepository extends JpaRepository<FranchiseProduct, Long> {
    FranchiseProduct findByLink(String link);

    List<FranchiseProduct> findByTitle(String title);
}
