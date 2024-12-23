package com.distribuidorasafari.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.distribuidorasafari.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    List<Category> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
