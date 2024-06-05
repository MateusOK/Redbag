package br.com.redbag.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AnimalRequestDto(

        @NotBlank(message = "{animalName.not.blank}")
        String name,
        @NotNull(message = "{animalAge.not.null}")
        @PositiveOrZero(message = "{animalAge.positive.or.zero}")
        Integer age,
        @NotBlank(message = "{animalGender.not.blank}")
        String gender,
        @NotNull(message = "{animalWeight.not.null}")
        @Positive(message = "{animalWeight.positive}")
        Double weight

) {
}
