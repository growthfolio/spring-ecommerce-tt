package com.distribuidorasafari.ecommerce.service;

import com.distribuidorasafari.ecommerce.model.Cart;
import com.distribuidorasafari.ecommerce.model.User;
import com.distribuidorasafari.ecommerce.repository.CartRepository;
import com.distribuidorasafari.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Verifica se o usuário já tem um carrinho. Caso não tenha, cria um.
     *
     * @param userId ID do usuário.
     * @return O carrinho do usuário.
     */
    public Cart verifyAndCreateCart(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        User user = userOpt.get();

        if (user.getCart() != null) {
            return user.getCart();
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        user.setCart(cart);
        userRepository.save(user);

        return cart;
    }
}
