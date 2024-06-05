package br.com.redbag.api.dto.response;

import br.com.redbag.api.entity.User;

public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email
) {
    public UserResponseDto(User response) {
        this(response.getId(), response.getName(), response.getUsername(), response.getEmail());
    }
}