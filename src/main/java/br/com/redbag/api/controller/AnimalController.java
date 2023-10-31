package br.com.redbag.api.controller;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import br.com.redbag.api.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/animals", produces = {"application/json"})
@Tag(name = "Animals")
@SecurityRequirement(name = "bearerAuth")
public class AnimalController {

    private final AnimalService animalService;

    @Operation(summary = "Save a new animal", method = "POST")
    @PostMapping(value = "/{userId}", consumes = {"application/json"})
    public ResponseEntity<AnimalResponseDto> saveAnimal(@RequestBody @Valid AnimalRequestDto requestDto, @PathVariable @Positive Long userId, UriComponentsBuilder builder) {
        var response = animalService.saveAnimal(requestDto, userId);
        var uri = builder.path("animals/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Upload an image for an animal", method = "POST")
    @PostMapping(value = "/upload/{animalId}", consumes = {"multipart/form-data"})
    public ResponseEntity<AnimalResponseDto> uploadImage(@PathVariable @Positive Long animalId, @RequestPart("file")MultipartFile file){
        var response = animalService.uploadImage(animalId, file);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all animals from a user", method = "GET")
    @GetMapping("{userId}")
    public ResponseEntity<List<AnimalResponseDto>> getAllUserAnimals(@PathVariable @Positive Long userId) {
        var response = animalService.getAllUserAnimals(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get an animal from a user", method = "GET")
    @GetMapping("/{userId}/{animalId}")
    public ResponseEntity<AnimalResponseDto> getUserAnimalById(@PathVariable @Positive Long userId, @PathVariable @Positive Long animalId) {
        var response = animalService.getUserAnimalById(userId, animalId);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Delete an animal from a user", method = "DELETE")
    @DeleteMapping("/{userId}/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable @Positive Long userId, @PathVariable Long animalId) {
        animalService.deleteAnimal(userId, animalId);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Update an animal from a user", method = "PUT")
    @PutMapping(value = "/{userId}/{animalId}", consumes = {"application/json"})
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable @Positive Long userId, @PathVariable @Positive Long animalId, @RequestBody @Valid AnimalRequestDto request) {
        var response = animalService.updateAnimal(userId, animalId, request);
        return ResponseEntity.ok(response);
    }
}