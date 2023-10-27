package br.com.redbag.api.dto.response;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.enums.Gender;

public record AnimalResponseDto(

        Long id,
        String name,
        Integer age,
        Gender gender,
        Double weight,
        Image imageDetails

) {

    public AnimalResponseDto(Animal response){
        this(response.getId(), response.getName(), response.getAge(),
                response.getGender(), response.getWeight(), response.getImageDetails());
    }
}