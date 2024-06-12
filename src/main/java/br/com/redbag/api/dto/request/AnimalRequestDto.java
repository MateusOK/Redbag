package br.com.redbag.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AnimalRequestDto(

        @NotBlank(message = "{animalName.not.blank}")
        String name,
        @NotNull(message = "{animalColor.not.blank}")
        String color

) {
}
