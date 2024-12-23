package com.distribuidorasafari.ecommerce.repository;

import com.distribuidorasafari.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByNameContainingIgnoreCase(String name);
    Optional<User> findByEmail(String email);

}
