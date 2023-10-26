package br.com.redbag.api.dto.response;

import br.com.redbag.api.entity.Animal;

public record AnimalResponseDto(

        String id,
        String name,
        String color,
        String imageId

) {

    public AnimalResponseDto(Animal response){
        this(response.getId(), response.getName(), response.getColor(), response.getImageId());
    }

}
