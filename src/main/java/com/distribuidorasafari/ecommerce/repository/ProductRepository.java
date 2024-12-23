package com.distribuidorasafari.ecommerce.repository;

import com.distribuidorasafari.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
