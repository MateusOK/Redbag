package br.com.redbag.api.service.impl;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.exceptions.ResourceAlreadyExistsException;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userRepository.delete(user);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (!user.getUsername().equalsIgnoreCase(request.username())) {
            if (Boolean.TRUE.equals(userRepository.existsByUsername(request.username()))) {
                throw new ResourceAlreadyExistsException("Username already exists!");
            }
            user.setUsername(request.username());
        }

        if (!user.getEmail().equalsIgnoreCase(request.email())) {
            if (Boolean.TRUE.equals(userRepository.existsByEmail(request.email()))) {
                throw new ResourceAlreadyExistsException("Email already exists!");
            }
            user.setEmail(request.email());
        }

        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}