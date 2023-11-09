package br.com.redbag.api.service.impl;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;
import br.com.redbag.api.dto.response.LoginResponseDto;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.enums.UserRole;
import br.com.redbag.api.exceptions.ResourceAlreadyExistsException;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.security.TokenService;
import br.com.redbag.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.usernameOrEmail(), loginDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return new LoginResponseDto(tokenService.generateToken((User) auth.getPrincipal()));
    }

    @Override
    public String register(RegisterRequestDto request) {

        if(userRepository.existsByUsername(request.username())){
            throw new ResourceAlreadyExistsException("Username already exists!.");
        }
        if(userRepository.existsByEmail(request.email())){
            throw new ResourceAlreadyExistsException("Email already exists!.");
        }

        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return "User registered successfully!.";
    }
}