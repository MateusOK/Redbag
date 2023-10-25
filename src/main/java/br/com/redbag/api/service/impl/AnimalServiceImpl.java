package br.com.redbag.api.service.impl;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Override
    public AnimalResponseDto saveAnimal(AnimalRequestDto request, String userId) {
        User user = findUserById(userId);
        var animal = animalRepository.save(new Animal(request));
        user.getAnimals().add(new Animal(request));

        return new AnimalResponseDto(animal);
    }

    @Override
    public List<AnimalResponseDto> getUserAnimals(String userId) {
        User user = findUserById(userId);
        List<Animal> animals = user.getAnimals();
        return animals.stream().map(AnimalResponseDto::new).toList();
    }

    @Override
    public void deleteAnimal(String userId, String animalId) {
        User user = findUserById(userId);
        Animal animal = findAnimalById(animalId);
        animalRepository.delete(animal);
        user.getAnimals().remove(animal);
        userRepository.save(user);
    }

    @Override
    public AnimalResponseDto updateAnimal(String userId, String animalId, AnimalRequestDto request) {
        User user = findUserById(userId);
        Animal animal = findAnimalById(animalId);

        if (!user.getAnimals().contains(animal)){
            throw new RuntimeException("Animal does not belong to this user");
        } else {
            user.getAnimals().remove(animal);
        }

        animal.setColor(request.color());
        animal.setName(request.name());

        Animal updatedAnimal = animalRepository.save(animal);
        user.getAnimals().add(updatedAnimal);
        userRepository.save(user);

        return new AnimalResponseDto(updatedAnimal);

    }

    private User findUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Animal findAnimalById(String animalId){
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
    }
}
