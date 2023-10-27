package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import br.com.redbag.api.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/animals/{userId}")
    public ResponseEntity<AnimalResponseDto> saveAnimal(@RequestBody AnimalRequestDto requestDto, @PathVariable Long userId, UriComponentsBuilder builder) {
        var response = animalService.saveAnimal(requestDto, userId);
        var uri = builder.path("animals/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("/animals/{userId}")
    public ResponseEntity<List<AnimalResponseDto>> getUserAnimals(@PathVariable Long userId) {
        var response = animalService.getUserAnimals(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/animals/{userId}/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long userId, @PathVariable Long animalId) {
        animalService.deleteAnimal(userId, animalId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/animals/{userId}/{animalId}")
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable Long userId, @PathVariable Long animalId, @RequestBody AnimalRequestDto request) {
        var response = animalService.updateAnimal(userId, animalId, request);
        return ResponseEntity.ok(response);
    }
}