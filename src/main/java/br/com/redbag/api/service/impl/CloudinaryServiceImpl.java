package br.com.redbag.api.service.impl;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.History;
import br.com.redbag.api.enums.HealthStatus;
import br.com.redbag.api.exceptions.ImageUploadException;
import br.com.redbag.api.exceptions.ResourceNotFoundException;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.service.CloudinaryService;
import br.com.redbag.api.utils.PredictionResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final AnimalRepository animalRepository;

    @Value("${app.prediction-url}")
    private static String PREDICTION_URL;
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map<String, String> options = ObjectUtils.asMap();
            Map<String, Object> response = cloudinary.uploader().upload(file.getBytes(), options);

            String publicId = response.get("public_id").toString();
            String url = response.get("url").toString();

            return Map.of("public_id", publicId, "url", url);
        } catch (Exception e) {
            throw new ImageUploadException("Error while uploading image");
        }
    }

    @Override
    public PredictionResponse predict(MultipartFile file) {
        try {
            Map<String, String> image = uploadImage(file);
            String predictionURI = PREDICTION_URL + image.get("public_id");
            ResponseEntity<PredictionResponse> prediction = restTemplate.getForEntity(predictionURI, PredictionResponse.class);
            return prediction.getBody();
        } catch (Exception e) {
            throw new ImageUploadException("Error while uploading image");
        }
    }

    @Override
    public PredictionResponse predictAndStore(MultipartFile file, Long animalId) {
        try {
            Animal animal = animalRepository.findById(animalId)
                    .orElseThrow(() -> new ResourceNotFoundException("Animal not found"));
            PredictionResponse results = predict(file);
            History history = new History();
            history.setAnimal(animal);
            history.setHealthStatus(HealthStatus.valueOf(results.getPredictedClass().toUpperCase()));
            history.setAccuracy(results.getConfidence());
            history.setDate(new Date());
            history.setTime(LocalTime.now());

            animal.getHealthHistory().add(history);
            animalRepository.save(animal);

            return results;
        } catch (Exception e) {
            throw new ImageUploadException("Error while uploading image");
        }
    }
}