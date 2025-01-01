package com.distribuidorasafari.ecommerce.controller;

import java.util.Optional;

import com.distribuidorasafari.ecommerce.model.Cart;
import com.distribuidorasafari.ecommerce.model.CartItem;
import com.distribuidorasafari.ecommerce.model.Product;
import com.distribuidorasafari.ecommerce.repository.CartItemRepository;
import com.distribuidorasafari.ecommerce.repository.ProductRepository;

import com.distribuidorasafari.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        try {
            Cart cart = cartService.verifyAndCreateCart(userId);
            return ResponseEntity.ok(cart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        if (cartItem.getProduct() == null || cartItem.getProduct().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto inválido ou não informado.");
        }

        Cart cart = cartService.verifyAndCreateCart(userId);

        Optional<Product> productOpt = productRepository.findById(cartItem.getProduct().getId());
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto inválido ou não encontrado.");
        }

        Product product = productOpt.get();
        if (product.getAmount() < cartItem.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade indisponível em estoque.");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }

        product.setAmount(product.getAmount() - cartItem.getQuantity());
        productRepository.save(product);

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}/remove/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.verifyAndCreateCart(userId);

        Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemId);
        if (itemOpt.isEmpty() || !itemOpt.get().getCart().equals(cart)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado no carrinho.");
        }

        CartItem itemToRemove = itemOpt.get();

        Product product = itemToRemove.getProduct();
        product.setAmount(product.getAmount() + itemToRemove.getQuantity());
        productRepository.save(product);

        cartItemRepository.delete(itemToRemove);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{userId}/update/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        if (quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade inválida.");
        }

        Cart cart = cartService.verifyAndCreateCart(userId);

        Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemId);
        if (itemOpt.isEmpty() || !itemOpt.get().getCart().equals(cart)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado no carrinho.");
        }

        CartItem itemToUpdate = itemOpt.get();
        Product product = itemToUpdate.getProduct();
        int stockDifference = quantity - itemToUpdate.getQuantity();

        if (product.getAmount() < stockDifference) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estoque insuficiente.");
        }

        product.setAmount(product.getAmount() - stockDifference);
        productRepository.save(product);

        itemToUpdate.setQuantity(quantity);
        cartItemRepository.save(itemToUpdate);

        return ResponseEntity.ok(cart);
    }
}
