package com.distribuidorasafari.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distribuidorasafari.ecommerce.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Busca todos os itens do carrinho por ID do carrinho
    List<CartItem> findAllByCartId(Long cartId);
}
