package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.LoginRequestDto;
import br.com.redbag.api.dto.request.RegisterRequestDto;

public interface AuthService {

    String register(RegisterRequestDto request);

    String login(LoginRequestDto loginDto);
}
