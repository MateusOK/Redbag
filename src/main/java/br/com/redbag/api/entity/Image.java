package br.com.redbag.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @Column(name = "public_id")
    private String publicId;
    private String url;

    public Image(String url, String publicId) {
        this.url = url;
        this.publicId = publicId;
    }
}