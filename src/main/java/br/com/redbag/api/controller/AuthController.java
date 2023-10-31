package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;
import br.com.redbag.api.dto.response.LoginResponseDto;
import br.com.redbag.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentications")
public class AuthController {

    private final AuthService authService;
    @Operation(summary = "Login user and returns JWT Token", method = "POST")
    @PostMapping(value = {"/login", "/signin"}, consumes = {"application/json"})
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto){
        var token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Register", method = "POST")
    @PostMapping(value = {"/register", "/signup"}, consumes = {"application/json"})
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}