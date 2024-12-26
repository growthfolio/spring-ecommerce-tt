package com.distribuidorasafari.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.distribuidorasafari.ecommerce.model.User;
import com.distribuidorasafari.ecommerce.model.UserLogin;
import com.distribuidorasafari.ecommerce.repository.UserRepository;
import com.distribuidorasafari.ecommerce.security.JwtService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isPresent() && existingUser.get().getId() != user.getId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

        // Create authentication object
        var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(), userLogin.get().getPassword());

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(credentials);

        // If authentication is successful
        if (authentication.isAuthenticated()) {

            // Retrieve user data
            Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

            // If user is found
            if (user.isPresent()) {

                // Fill the object with the retrieved data
                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setPhoto(user.get().getPhoto());
                userLogin.get().setToken(generateToken(userLogin.get().getEmail()));
                userLogin.get().setPassword("");

                // Return the populated object
                return userLogin;

            }

        }

        return Optional.empty();

    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

    private String generateToken(String user) {
        return "Bearer " + jwtService.generateToken(user);
    }

}
