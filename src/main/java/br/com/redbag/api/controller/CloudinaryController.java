package br.com.redbag.api.controller;

import br.com.redbag.api.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
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
    public String predict(@RequestPart("file") MultipartFile file) throws IOException {
        return cloudinaryService.predict(file);
    }
}