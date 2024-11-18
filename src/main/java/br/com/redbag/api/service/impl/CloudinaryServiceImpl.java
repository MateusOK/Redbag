package br.com.redbag.api.service.impl;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.History;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.enums.HealthStatus;
import br.com.redbag.api.exceptions.ImageUploadException;
import br.com.redbag.api.exceptions.ResourceNotFoundException;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.repository.ImageRepository;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final AnimalRepository animalRepository;
    private final ImageRepository imageRepository;

    @Value("${app.prediction-url}")
    private String predictionUrl;
    private static final String MESSAGE_ERROR = "Error while uploading image";

    private final RestTemplate restTemplate;

    @Override
    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map<String, String> options = ObjectUtils.asMap("format", "jpg");
            Map<String, Object> response = cloudinary.uploader().upload(file.getBytes(), options);

            String publicId = response.get("public_id").toString();
            String url = response.get("url").toString();

            return Map.of("public_id", publicId, "url", url);
        } catch (Exception e) {
            throw new ImageUploadException(MESSAGE_ERROR);
        }
    }

    @Override
    public PredictionResponse predict(MultipartFile file) {
        try {
            Map<String, String> image = uploadImage(file);
            String predictionURI = predictionUrl + image.get("public_id");
            ResponseEntity<PredictionResponse> prediction = restTemplate.getForEntity(predictionURI, PredictionResponse.class);
            return prediction.getBody();
        } catch (Exception e) {
            throw new ImageUploadException(MESSAGE_ERROR);
        }
    }

    @Override
    public PredictionResponse predictAndStore(MultipartFile file, Long animalId) {
        try {
            Animal animal = animalRepository.findById(animalId)
                    .orElseThrow(() -> new ResourceNotFoundException("Animal not found"));
            PredictionResponse results = predict(file);
            Map<String, String> image = uploadImage(file);
            Image imageDetails = new Image(image.get("url"), image.get("public_id"));
            imageRepository.save(imageDetails);
            History history = new History();
            history.setAnimal(animal);
            history.setHealthStatus(HealthStatus.valueOf(results.getPredictedClass().toUpperCase()));
            history.setAccuracy(results.getConfidence());
            history.setImage(imageDetails);
            history.setTime(LocalDateTime.now(ZoneOffset.of("-03:00")));

            animal.getHealthHistory().add(history);
            animalRepository.save(animal);

            return results;
        } catch (Exception e) {
            throw new ImageUploadException(MESSAGE_ERROR);
        }
    }
}