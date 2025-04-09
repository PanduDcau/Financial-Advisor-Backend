package com.example.authdemo.controller;

import com.example.authdemo.dto.IncomeRequest;
import com.example.authdemo.dto.LevelRequest;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;
import com.example.authdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final UserService userService;
    private final UserRepository userRepository;

    public IncomeController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> addIncome(@RequestBody IncomeRequest request,
                                       Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.addIncomeToUser(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getIncome(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user.getIncomeEntries());
    }

    @GetMapping("/level")
    public ResponseEntity<Integer> getLevel(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user.getLevel());
    }

    @PutMapping("/level")
    public ResponseEntity<?> updateLevel(@RequestBody LevelRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateUserLevel(user, request.level());
        return ResponseEntity.ok().build();
    }
}