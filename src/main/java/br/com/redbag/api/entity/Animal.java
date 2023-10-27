package br.com.redbag.api.entity;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @OneToOne
    @JoinColumn(name = "images_id")
    private Image imageDetails;

    public Animal(AnimalRequestDto request){
        this.name = request.name();
        this.color = request.color();
    }

    public Animal(Animal animal){
        this.id = animal.getId();
        this.name = animal.getName();
        this.color = animal.getColor();
        this.imageDetails = animal.getImageDetails();
    }
}
