package br.com.redbag.api.dto.request;

import br.com.redbag.api.enums.Gender;

public record AnimalRequestDto(

        String name,
        Integer age,
        Gender gender,
        Double weight

) {
}
