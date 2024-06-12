package br.com.redbag.api.dto.response;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.History;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.enums.Gender;

import java.util.List;

public record AnimalResponseDto(

        Long id,
        String name,
        String color,
        Image imageDetails,
        List<History> healthHistory

) {

    public AnimalResponseDto(Animal response){
        this(response.getId(), response.getName(), response.getColor(), response.getImageDetails(), response.getHealthHistory());
    }
}