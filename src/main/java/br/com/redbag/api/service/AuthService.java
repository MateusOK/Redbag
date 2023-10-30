package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;
import br.com.redbag.api.dto.response.LoginResponseDto;

public interface AuthService {

    String register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto loginDto);
}
