package br.com.redbag.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank(message = "{registerName.not.blank}")
        String name,
        @NotBlank(message = "{registerUsername.not.blank}")
        String username,
        @NotBlank(message = "{registerEmail.not.blank}")
        @Email
        String email,
        @NotBlank(message = "{registerPassword.not.blank}")
        String password
) {
}