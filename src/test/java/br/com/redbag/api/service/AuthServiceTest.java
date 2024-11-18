package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;
import br.com.redbag.api.dto.response.LoginResponseDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.enums.UserRole;
import br.com.redbag.api.exceptions.ResourceAlreadyExistsException;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.security.TokenService;
import br.com.redbag.api.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthServiceImpl authService;


    private User user;
    private RegisterRequestDto registerDto;
    private LoginRequestDto loginDto;


    @BeforeEach
    public void setup(){
        user = new User();
        user.setId(1L);
        user.setName("TesteUser");
        user.setUsername("testeUser");
        user.setEmail("teste@teste.com");
        user.setRole(UserRole.USER);
        user.setPassword("12345");

        registerDto = new RegisterRequestDto("Teste", "testeUser", "teste@teste.com", "123456");
        loginDto = new LoginRequestDto("TesteUser", "123456");

    }

    @Test
    @DisplayName("Should register a user successfully")
    void testRegisterSuccess() {
        when(userRepository.existsByUsername(registerDto.username())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.email())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.password())).thenReturn("123456");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto response = authService.register(registerDto);

        assertEquals("testeUser", response.username());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail to register a user if username already exists")
    void testRegisterUsernameAlreadyExists(){
        when(userRepository.existsByUsername(registerDto.username())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(registerDto));
    }

    @Test
    @DisplayName("Should fail to register a user if email already exists")
    void testRegisterEmailAlreadyExists(){
        when(userRepository.existsByUsername(registerDto.username())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.email())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(registerDto));
    }

    @Test
    @DisplayName("Should login successfully")
    void testLoginSuccess() {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.usernameOrEmail(), loginDto.password());
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        when(tokenService.generateToken(user)).thenReturn("mockedToken");

        LoginResponseDto response = authService.login(loginDto);
        assertEquals("mockedToken", response.token());
        assertEquals(1L, response.userId());
    }
}