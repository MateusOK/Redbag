package br.com.redbag.api.service;

import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.exceptions.ImageUploadException;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.repository.ImageRepository;
import br.com.redbag.api.service.impl.CloudinaryServiceImpl;
import br.com.redbag.api.utils.PredictionResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CloudinaryServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private CloudinaryServiceImpl cloudinaryService;

    @Value("${app.prediction-url}")
    private String predictionUrl;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        when(cloudinary.uploader()).thenReturn(uploader);

        ReflectionTestUtils.setField(cloudinaryService, "predictionUrl", "http://redbag.com/predict/");
    }

    @Test
    @DisplayName("Should upload image for animal successfully")
    void testUploadImageSuccess() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Map<String, Object> uploadResponse = Map.of("public_id", "image123", "url", "http://redbag.com/image.jpg");

        when(file.getBytes()).thenReturn(new byte[0]);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(uploadResponse);

        Map<String, String> result = cloudinaryService.uploadImage(file);

        assertEquals("image123", result.get("public_id"));
        assertEquals("http://redbag.com/image.jpg", result.get("url"));
    }

    @Test
    @DisplayName("Should throw ImageUploadException when upload image fails")
    void testUploadImageFailure() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new RuntimeException("Upload error"));

        assertThrows(ImageUploadException.class, () -> cloudinaryService.uploadImage(file));
    }

    @Test
    @DisplayName("Should predict a image successfully")
    void testPredict() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        String predictionURI = "http://redbag.com/predict/image123";
        PredictionResponse predictionResponse = new PredictionResponse("healthy", 0.9);

        when(file.getBytes()).thenReturn(new byte[0]);
        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenReturn(Map.of("public_id", "image123", "url", "http://redbag.com/image.jpg"));

        ResponseEntity<PredictionResponse> responseEntity = new ResponseEntity<>(predictionResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(predictionURI, PredictionResponse.class)).thenReturn(responseEntity);

        PredictionResponse result = cloudinaryService.predict(file);

        assertEquals("healthy", result.getPredictedClass());
        assertEquals(0.9, result.getConfidence());
    }

    @Test
    @DisplayName("Should throw ImageUploadException when predict fails")
    void testPredictFailure() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new RuntimeException("Upload error"));

        assertThrows(ImageUploadException.class, () -> cloudinaryService.predict(file));
    }

    @Test
    @DisplayName("Should predict and store image for animal successfully")
    void testPredictAndStore() throws Exception {
        Long animalId = 1L;
        Animal animal = new Animal();
        animal.setId(animalId);
        animal.setHealthHistory(new ArrayList<>());

        MultipartFile file = mock(MultipartFile.class);
        String predictionURI = "http://redbag.com/predict/image123";
        PredictionResponse predictionResponse = new PredictionResponse("healthy", 0.9);

        when(file.getBytes()).thenReturn(new byte[0]);
        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenReturn(Map.of("public_id", "image123", "url", "http://redbag.com/image.jpg"));

        ResponseEntity<PredictionResponse> responseEntity = new ResponseEntity<>(predictionResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(predictionURI, PredictionResponse.class)).thenReturn(responseEntity);

        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        when(imageRepository.save(any(Image.class))).thenReturn(new Image("http://redbag.com/image.jpg", "image123"));

        PredictionResponse result = cloudinaryService.predictAndStore(file, animal.getId());

        assertEquals("healthy", result.getPredictedClass());
        assertEquals(0.9, result.getConfidence());
        assertEquals(1, animal.getHealthHistory().size());
        assertEquals("HEALTHY", animal.getHealthHistory().get(0).getHealthStatus().name());
        assertEquals(0.9, animal.getHealthHistory().get(0).getAccuracy());
    }

    @Test
    @DisplayName("Should throw ImageUploadException when predict and store fails")
    void testPredictAndStoreFailure() throws Exception {
        Long animalId = 1L;
        Animal animal = new Animal();
        animal.setId(animalId);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new RuntimeException("Upload error"));

        assertThrows(ImageUploadException.class, () -> cloudinaryService.predictAndStore(file, animal.getId()));
    }
}