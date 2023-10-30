package br.com.redbag.api.dto.request;

public record RegisterRequestDto(
        String name,
        String username,
        String email,
        String password
) {
}