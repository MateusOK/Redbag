package br.com.redbag.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map<String, String> uploadImage(MultipartFile file) throws IOException;

    String predict(MultipartFile file) throws IOException;
}
