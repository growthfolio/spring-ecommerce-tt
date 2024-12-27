package com.distribuidorasafari.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distribuidorasafari.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Busca todos os pedidos de um usuário
    List<Order> findAllByUserId(Long userId);
}