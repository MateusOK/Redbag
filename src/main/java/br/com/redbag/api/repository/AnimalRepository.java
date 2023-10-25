package br.com.redbag.api.repository;

import br.com.redbag.api.entity.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalRepository extends MongoRepository<Animal, String> {
}
