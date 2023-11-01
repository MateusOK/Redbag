package br.com.redbag.api.entity;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import br.com.redbag.api.enums.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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