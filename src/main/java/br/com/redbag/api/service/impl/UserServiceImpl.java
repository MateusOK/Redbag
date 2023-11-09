package br.com.redbag.api.service.impl;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.exceptions.ForbiddenUserRequestException;
import br.com.redbag.api.exceptions.ResourceAlreadyExistsException;
import br.com.redbag.api.exceptions.ResourceNotFoundException;
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
    public void deleteUser(Long userId, Authentication authentication) {
        User user = returnUserById(userId);

        if (authentication.getName().equals(user.getUsername())) {
            userRepository.delete(user);
        } else {
            throw new ForbiddenUserRequestException("You can't delete another user!");
        }
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto request, Long userId, Authentication authentication) {
        User user = returnUserById(userId);

        if (user.getUsername().equalsIgnoreCase(request.username())){
            user.setUsername(request.username());
        } else if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException("Username already exists!.");
        }

        if (user.getEmail().equalsIgnoreCase(request.email())){
            user.setEmail(request.email());
        } else if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Email already exists!.");
        }

        if (!authentication.getName().equals(user.getUsername())) {
            throw new ForbiddenUserRequestException("You can't update another user credentials!");
        }

        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    private User returnUserById(Long UserId){
        return userRepository.findById(UserId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}