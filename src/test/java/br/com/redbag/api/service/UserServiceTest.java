package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.exceptions.ResourceAlreadyExistsException;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDto userRequestDto;



    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setName("Joao");
        user.setUsername("JoaoSilva");
        user.setEmail("joao@teste.com");
        user.setPassword("12345");

        userRequestDto = new UserRequestDto("Maria", "MariaSilva", "maria@gmail.com", "123456");

        when(authentication.getPrincipal()).thenReturn(user);

    }

    @Test
    @DisplayName("Should delete a user successfully")
    void testDeleteUser() {
        userService.deleteUser(authentication);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Should update a user successfully")
    void testUpdateUserSuccess() {
        when(userRepository.existsByUsername(userRequestDto.username())).thenReturn(false);
        when(userRepository.existsByEmail(userRequestDto.email())).thenReturn(false);
        when(passwordEncoder.encode(userRequestDto.password())).thenReturn("123456");

        UserResponseDto response = userService.updateUser(userRequestDto, authentication);

        assertEquals("MariaSilva", response.username());
        assertEquals("maria@gmail.com", response.email());
        assertEquals("Maria", response.name());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should not update a user if username already exists")
    void testUpdateUserUsernameAlreadyExists() {
        when(userRepository.existsByUsername(userRequestDto.username())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUser(userRequestDto, authentication));

    }


    @Test
    @DisplayName("Should not update a user if email already exists")
    void testUpdateUserEmailAlreadyExists() {
        when(userRepository.existsByEmail(userRequestDto.email())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUser(userRequestDto, authentication));

    }

}