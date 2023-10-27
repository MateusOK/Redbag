package br.com.redbag.api.entity;

import br.com.redbag.api.enums.HealthStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;
    private Double accuracy;
    private Date date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

}
