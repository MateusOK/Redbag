package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users", produces = {"application/json"})
@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Delete a user by id", method = "DELETE")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, Authentication authentication) {
        userService.deleteUser(userId, authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user by id", method = "PUT")
    @PutMapping(value = "/{userId}", consumes = {"application/json"})
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto request, @PathVariable Long userId, Authentication authentication) {
        var response = userService.updateUser(request, userId, authentication);
        return ResponseEntity.ok(response);
    }
}