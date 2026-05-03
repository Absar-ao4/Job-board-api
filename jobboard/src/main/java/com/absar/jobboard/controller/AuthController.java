package com.absar.jobboard.controller;

import com.absar.jobboard.model.User;
import com.absar.jobboard.repository.UserRepository;
import com.absar.jobboard.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        Optional<User> existing=userRepository
                .findByEmail(user.getEmail());
        if(existing.isPresent()){
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token=jwtUtil.generateToken(user.getEmail());
        Map<String,String> response=new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existing = userRepository
                .findByEmail(user.getEmail());
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }
        if (!passwordEncoder.matches(
                user.getPassword(),
                existing.get().getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
