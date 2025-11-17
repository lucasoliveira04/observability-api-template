package com.jenkins.apiwithjenkins.controller;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        var response = userService.saveUser(userDto);
        try {
            return ResponseEntity.ok("User created with ID: " + response.id().toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    };

    @GetMapping("/all")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, "getAllFromPostgres"));
    }

    @GetMapping("/all-redis")
    public ResponseEntity<Page<UserResponse>> getAllUsersFromRedis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, "getAllFromRedis"));
    }

    @GetMapping("/by-ids")
    public ResponseEntity<Iterable<Users>> getUsersByIds(
            @RequestParam List<UUID> userIds
    ) {
        try {
            return ResponseEntity.ok(userService.findByUserIds(userIds));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
