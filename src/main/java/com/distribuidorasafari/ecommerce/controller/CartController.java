package com.distribuidorasafari.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.distribuidorasafari.ecommerce.model.Cart;
import com.distribuidorasafari.ecommerce.model.CartItem;
import com.distribuidorasafari.ecommerce.model.Product;
import com.distribuidorasafari.ecommerce.repository.CartRepository;
import com.distribuidorasafari.ecommerce.repository.ProductRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (cartOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Cart cart = cartOpt.get();
        Optional<Product> productOpt = productRepository.findById(cartItem.getProduct().getId());
        if (productOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Product product = productOpt.get();
        cartItem.setProduct(product);
        cartItem.setUnitPrice(product.getPrice());
        cart.addCartItem(cartItem);

        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @DeleteMapping("/{userId}/remove/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (cartOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Cart cart = cartOpt.get();
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/update/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (cartOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Cart cart = cartOpt.get();
        for (CartItem item : cart.getCartItems()) {
            if (item.getId().equals(cartItemId)) {
                item.setQuantity(quantity);
                break;
            }
        }

        return ResponseEntity.ok(cartRepository.save(cart));
    }
}
