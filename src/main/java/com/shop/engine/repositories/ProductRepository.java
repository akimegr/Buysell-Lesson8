package com.shop.engine.repositories;

import com.shop.engine.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String title);
    Product findByLink(String link);
}
