package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.dto.response.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface UserService {

    void deleteUser(Authentication authentication);

    UserResponseDto updateUser(UserRequestDto request, Authentication authentication);
}
