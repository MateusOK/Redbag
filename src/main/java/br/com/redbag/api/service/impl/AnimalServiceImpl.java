package br.com.redbag.api.service.impl;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.enums.Gender;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.repository.ImageRepository;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.service.AnimalService;
import br.com.redbag.api.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public AnimalResponseDto saveAnimal(AnimalRequestDto request, Long userId) {
        User user = findUserById(userId);
        Animal animal = new Animal(request);

        animal.setUser(user);
        user.getAnimals().add(animal);

        return new AnimalResponseDto(animalRepository.save(animal));
    }

    @Override
    public List<AnimalResponseDto> getAllUserAnimals(Long userId) {
        User user = findUserById(userId);
        List<Animal> animals = user.getAnimals();
        return animals.stream().map(AnimalResponseDto::new).toList();
    }

    @Override
    public AnimalResponseDto getUserAnimalById(Long userId, Long animalId) {
        User user = findUserById(userId);
        Animal animal = findAnimalById(animalId);

        if (!user.getAnimals().contains(animal)){
            throw new RuntimeException("Animal does not belong to this user");
        } else {
            Integer index = user.getAnimals().indexOf(animal);
            animal = user.getAnimals().get(index);
        }
        return new AnimalResponseDto(animal);
    }

    @Override
    public void deleteAnimal(Long userId, Long animalId) {
        User user = findUserById(userId);
        Animal animal = findAnimalById(animalId);
        animalRepository.delete(animal);
        user.getAnimals().remove(animal);
        userRepository.save(user);
    }

    @Override
    public AnimalResponseDto updateAnimal(Long userId, Long animalId, AnimalRequestDto request) {
        User user = findUserById(userId);
        Animal animal = findAnimalById(animalId);

        if (!user.getAnimals().contains(animal)){
            throw new RuntimeException("Animal does not belong to this user");
        } else {
            user.getAnimals().remove(animal);
        }

        animal.setName(request.name());
        animal.setAge(request.age());
        animal.setGender(Gender.fromString(request.gender()));
        animal.setWeight(request.weight());

        Animal updatedAnimal = animalRepository.save(animal);
        user.getAnimals().add(updatedAnimal);
        userRepository.save(user);

        return new AnimalResponseDto(updatedAnimal);

    }

    @Override
    public AnimalResponseDto uploadImage(Long animalId, MultipartFile file) throws IOException {
        Animal animal = findAnimalById(animalId);
        Map<String, String> imageDetails = cloudinaryService.uploadImage(file);
        Image image = new Image(imageDetails.get("url"), imageDetails.get("public_id"));

        imageRepository.save(image);
        animal.setImageDetails(image);
        return new AnimalResponseDto(animalRepository.save(animal));
    }

    private User findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Animal findAnimalById(Long animalId){
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
    }
}
