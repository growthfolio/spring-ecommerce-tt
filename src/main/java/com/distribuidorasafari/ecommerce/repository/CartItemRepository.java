package com.distribuidorasafari.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.distribuidorasafari.ecommerce.model.Cart;
import com.distribuidorasafari.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distribuidorasafari.ecommerce.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartId(Long cartId);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findAllByCart(Cart cart);
}
