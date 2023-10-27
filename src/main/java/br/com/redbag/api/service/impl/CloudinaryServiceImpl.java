package br.com.redbag.api.service.impl;

import br.com.redbag.api.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        Map<String, String> options = ObjectUtils.asMap();
        Map<String, Object> response = cloudinary.uploader().upload(file.getBytes(), options);

        String publicId = response.get("public_id").toString();
        String url = response.get("url").toString();

        return Map.of("public_id", publicId, "url", url);
    }

    @Override
    public String predict(MultipartFile file) throws IOException {
        Map<String, String> image = uploadImage(file);
        String predictionURI = "http://127.0.0.1:8000/result/" + image.get("public_id");
        ResponseEntity<String> prediction = restTemplate.getForEntity(predictionURI, String.class);
        return prediction.getBody();
    }
}
