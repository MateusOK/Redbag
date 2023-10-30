package br.com.redbag.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "{loginUsernameOrEmail.not.blank}")
        String usernameOrEmail,
        @NotBlank(message = "{loginPassword.not.blank}")
        String password) {
}
