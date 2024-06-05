package cz.mendelu.ea.pokemon.model.trainer;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrainerRepository extends CrudRepository<Trainer, Long> {

}
