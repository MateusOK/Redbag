package br.com.redbag.api.service;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.dto.response.AnimalResponseDto;
import br.com.redbag.api.entity.Animal;
import br.com.redbag.api.entity.Image;
import br.com.redbag.api.entity.User;
import br.com.redbag.api.exceptions.ResourceNotFoundException;
import br.com.redbag.api.repository.AnimalRepository;
import br.com.redbag.api.repository.ImageRepository;
import br.com.redbag.api.repository.UserRepository;
import br.com.redbag.api.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private CloudinaryService cloudinaryService;
    @InjectMocks
    private AnimalServiceImpl animalService;

    private User user;
    private Animal animal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");
        user.setUsername("user");
        user.setPassword("password");
        user.setEmail("user@test.com");
        user.setAnimals(new ArrayList<>());

        animal = new Animal();
        animal.setId(1L);
        animal.setName("Animal");
        animal.setColor("Black");

    }

    @Test
    @DisplayName("Should save animal")
    void testSaveAnimal() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalRequestDto animalRequestDto = new AnimalRequestDto("Animal", "Black");

        AnimalResponseDto response = animalService.saveAnimal(animalRequestDto, user.getId());

        assertEquals("Animal", response.name());
        assertEquals("Black", response.color());

    }

    @Test
    @DisplayName("Should get all animals from user")
    void testGetAllUserAnimals() {
        user.getAnimals().add(animal);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<AnimalResponseDto> animals = animalService.getAllUserAnimals(user.getId());

        assertEquals(1, animals.size());
        assertEquals("Animal", animals.get(0).name());

    }

    @Test
    @DisplayName("Should return animal by id")
    void testGetUserAnimalByIdSuccess() {
        user.getAnimals().add(animal);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));

        AnimalResponseDto response = animalService.getUserAnimalById(user.getId(), animal.getId());

        assertEquals("Animal", response.name());

    }

    @Test
    @DisplayName("Should throw exception when animal does not belong to user")
    void testGetUserAnimalByIdFail() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));

        assertThrows(ResourceNotFoundException.class, () -> animalService.getUserAnimalById(user.getId(), animal.getId()));

    }

    @Test
    @DisplayName("Should delete animal")
    void testDeleteAnimal() {
        user.getAnimals().add(animal);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));

        animalService.deleteAnimal(user.getId(), animal.getId());

        verify(animalRepository, times(1)).delete(animal);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    @DisplayName("Should update animal")
    void testUpdateAnimal() {
        user.getAnimals().add(animal);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        when(animalRepository.save(animal)).thenReturn(animal);

        AnimalRequestDto request = new AnimalRequestDto("Updated Animal", "White");

        AnimalResponseDto response = animalService.updateAnimal(user.getId(), animal.getId(), request);

        assertEquals("Updated Animal", response.name());
        assertEquals("White", response.color());
    }

    @Test
    @DisplayName("Should upload a image to animal successfully")
    void testUploadImageSuccess() {

        MultipartFile file = mock(MultipartFile.class);
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        when(cloudinaryService.uploadImage(file)).thenReturn(Map.of("url", "https://example.com/image.jpg", "public_id", "image123"));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalResponseDto response = animalService.uploadImage(animal.getId(), file);

        assertEquals("https://example.com/image.jpg", response.imageDetails().getUrl());
        verify(imageRepository, times(1)).save(any(Image.class));
    }
}