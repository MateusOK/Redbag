package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, Authentication authentication) {
        userService.deleteUser(userId, authentication);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/users/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto request, @PathVariable Long userId, Authentication authentication) {
        var response = userService.updateUser(request, userId, authentication);
        return ResponseEntity.ok(response);
    }
}