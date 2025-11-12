package com.moghoneim.user_service.controller;

import com.moghoneim.user_service.dto.UserDto;
import com.moghoneim.user_service.dto.UserRecord;
import com.moghoneim.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            UserRecord users = userService.getUser(username);
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user: " + ex.getMessage());
        }
    }

    @GetMapping("/debug/roles")
    public ResponseEntity<?> checkRoles(Authentication authentication) {
        return ResponseEntity.ok(authentication.getAuthorities());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser() {
        try {
            return ResponseEntity.ok(userService.getCurrentUserInfo());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        try {
            userService.updateUser(userId, userDto);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Update failed: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete failed: " + ex.getMessage());
        }
    }

    @GetMapping("/verification-link/{userId}")
    public ResponseEntity<String> sendVerificationLink(@PathVariable String userId) {
        try {
            userService.sendVerificationLink(userId);
            return ResponseEntity.ok("Verification link sent.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Verification failed: " + ex.getMessage());
        }
    }

    @GetMapping("/reset-password/{userId}")
    public ResponseEntity<String> sendResetPassword(@PathVariable String userId) {
        try {
            userService.sendResetPassword(userId);
            return ResponseEntity.ok("Password reset link sent.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Reset password failed: " + ex.getMessage());
        }
    }
}
