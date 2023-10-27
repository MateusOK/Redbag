package br.com.redbag.api.service;

import br.com.redbag.api.utils.PredictionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map<String, String> uploadImage(MultipartFile file) throws IOException;

    PredictionResponse predict(MultipartFile file) throws IOException;

    PredictionResponse predictAndStore(MultipartFile file, Long animalId) throws IOException;
}
