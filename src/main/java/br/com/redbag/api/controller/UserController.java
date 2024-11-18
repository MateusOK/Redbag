package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/users", produces = {"application/json"})
@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Delete a user", method = "DELETE")
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user", method = "PUT")
    @PutMapping(consumes = {"application/json"})
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserRequestDto request, Authentication authentication) {
        var response = userService.updateUser(request, authentication);
        return ResponseEntity.ok(response);
    }
}