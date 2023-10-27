package br.com.redbag.api.controller;

import br.com.redbag.api.service.CloudinaryService;
import br.com.redbag.api.utils.PredictionResponse;
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
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@RequestPart("file") MultipartFile file) throws IOException {
        var response = cloudinaryService.predict(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/predict/{animalId}")
    public ResponseEntity<PredictionResponse> predictAndStore(@RequestPart("file") MultipartFile file, @PathVariable Long animalId) throws IOException {
        var response = cloudinaryService.predictAndStore(file, animalId);
        return ResponseEntity.ok(response);
    }
}