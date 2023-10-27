package br.com.redbag.api.repository;

import br.com.redbag.api.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String>{
}
