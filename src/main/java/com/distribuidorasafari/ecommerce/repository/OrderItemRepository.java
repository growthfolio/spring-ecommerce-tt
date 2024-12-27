package com.distribuidorasafari.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distribuidorasafari.ecommerce.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Busca todos os itens de pedido por ID do pedido
    List<OrderItem> findAllByOrderId(Long orderId);
}
