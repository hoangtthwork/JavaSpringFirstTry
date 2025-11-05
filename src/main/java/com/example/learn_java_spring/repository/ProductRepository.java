package com.example.learn_java_spring.repository;

import com.example.learn_java_spring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    // @Query("SELECT p FROM Product p WHERE p.price < :maxPrice")
    // List<Product> findProductsCheaperThan(@Param("maxPrice") Double maxPrice);
}