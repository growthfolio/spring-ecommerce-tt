package com.distribuidorasafari.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distribuidorasafari.ecommerce.model.Cart;

@Repository
public interface    CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"user", "cartItems", "cartItems.product"})
    Optional<Cart> findByUserId(Long userId);

}
