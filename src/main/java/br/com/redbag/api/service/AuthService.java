package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;
import br.com.redbag.api.dto.response.LoginResponseDto;
import br.com.redbag.api.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto loginDto);
}
