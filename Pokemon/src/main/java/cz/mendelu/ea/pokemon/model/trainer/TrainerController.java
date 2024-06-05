package cz.mendelu.ea.pokemon.model.trainer;

import cz.mendelu.ea.pokemon.model.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.model.pokemon.PokemonResponse;
import cz.mendelu.ea.pokemon.utils.response.ArrayResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@Validated
public class TrainerController {
    private TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<TrainerResponse> getAllTrainers() {
        List<Trainer> trainers = trainerService.findAll();
        return ArrayResponse.of(
                trainers,
                TrainerResponse::new
        );
    }
}
