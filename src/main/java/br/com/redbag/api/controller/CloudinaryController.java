package br.com.redbag.api.controller;

import br.com.redbag.api.service.CloudinaryService;
import br.com.redbag.api.utils.PredictionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Cloudinary - Images")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;
    @Operation(summary = "Predict animal from image", method = "POST")
    @PostMapping(value = "/predict", consumes = {"multipart/form-data"})
    public ResponseEntity<PredictionResponse> predict(@RequestPart("file") MultipartFile file) throws IOException {
        var response = cloudinaryService.predict(file);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Predict animal from image and store result in database", method = "POST")
    @PostMapping(value = "/predict/{animalId}", consumes = {"multipart/form-data"})
    public ResponseEntity<PredictionResponse> predictAndStore(@RequestPart("file") MultipartFile file, @PathVariable Long animalId) throws IOException {
        var response = cloudinaryService.predictAndStore(file, animalId);
        return ResponseEntity.ok(response);
    }
}