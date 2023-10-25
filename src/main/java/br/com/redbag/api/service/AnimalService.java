package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;

import java.util.List;

public interface AnimalService {
    AnimalResponseDto saveAnimal(AnimalRequestDto request, String userId);

    List<AnimalResponseDto> getUserAnimals(String userId);

    void deleteAnimal(String userId, String animalId);

    AnimalResponseDto updateAnimal(String userId, String animalId, AnimalRequestDto request);

}
