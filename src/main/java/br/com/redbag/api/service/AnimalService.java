package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;

import java.util.List;

public interface AnimalService {
    AnimalResponseDto saveAnimal(AnimalRequestDto request, Long userId);

    List<AnimalResponseDto> getUserAnimals(Long userId);

    void deleteAnimal(Long userId, Long animalId);

    AnimalResponseDto updateAnimal(Long userId, Long animalId, AnimalRequestDto request);

}
