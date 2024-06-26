package br.com.redbag.api.service;

import br.com.redbag.api.utils.PredictionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {

    Map<String, String> uploadImage(MultipartFile file);

    PredictionResponse predict(MultipartFile file);

    PredictionResponse predictAndStore(MultipartFile file, Long animalId);
}
