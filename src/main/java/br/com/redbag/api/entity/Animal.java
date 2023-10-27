package br.com.redbag.api.entity;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.enums.Gender;
import br.com.redbag.api.enums.HealthStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Double weight;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "images_id")
    private Image imageDetails;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> healthHistory;

    public Animal(AnimalRequestDto request){
        this.name = request.name();
        this.gender = Gender.fromString(request.gender());
        this.age = request.age();
        this.weight = request.weight();
    }
}