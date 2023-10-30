package br.com.redbag.api.dto.request;

public record LoginRequestDto(String usernameOrEmail, String password) {
}
