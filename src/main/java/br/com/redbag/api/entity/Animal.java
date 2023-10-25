package br.com.redbag.api.entity;

import br.com.redbag.api.dto.request.AnimalRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "animals")
public class Animal {


    @Id
    private String id;
    private String name;
    private String color;
    private String imageId;

    public Animal(AnimalRequestDto request){
        this.name = request.name();
        this.color = request.color();
    }

}
