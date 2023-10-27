package br.com.redbag.api.dto.response;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.Image;

public record AnimalResponseDto(

        Long id,
        String name,
        String color,
        Image imageDetails

) {

    public AnimalResponseDto(Animal response){
        this(response.getId(), response.getName(), response.getColor(), response.getImageDetails());
    }

}
