package com.example.feedbackbd.controller;

import com.example.feedbackbd.model.User;
import com.example.feedbackbd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(user.getRole().equals("STUDENT") && (user.getStudentRegNo() == null || user.getStudentRegNo().length() != 12)) {
            return "Error: For student registration number must be 12 digits";
        }
        if(userService.findByUsername(user.getUsername()).isPresent()) {
            return "Error: Username already exists";
        }
        userService.saveUser(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Optional<User> optUser = userService.findByUsername(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        if(optUser.isPresent() && optUser.get().getPassword().equals(user.getPassword())) {
            response.put("authorized", true);
            response.put("role", optUser.get().getRole());
            return ResponseEntity.ok(response);
        }
        response.put("authorized", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
