package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalService {
    AnimalResponseDto saveAnimal(AnimalRequestDto request, Long userId);

    List<AnimalResponseDto> getAllUserAnimals(Long userId);

    void deleteAnimal(Long userId, Long animalId);

    AnimalResponseDto updateAnimal(Long userId, Long animalId, AnimalRequestDto request);

    AnimalResponseDto uploadImage(Long animalId, MultipartFile file);

    AnimalResponseDto getUserAnimalById(Long userId, Long animalId);
}
