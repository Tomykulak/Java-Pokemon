package cz.mendelu.ea.pokemon.domain.trainer;


import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;
import cz.mendelu.ea.pokemon.utils.response.ArrayResponse;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@Validated
@Tag(
        name = "Trainers",
        description = "Trainer endpoint."
)
public class TrainerController {

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
    private final TrainerService trainerService;

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "returns all trainers.",
            description = "returns all trainers"
    )
    public ArrayResponse<TrainerResponse> getAllTrainers() {
        List<Trainer> trainers = trainerService.findAll();
        return ArrayResponse.of(
                trainers,
                TrainerResponse::new
        );
    }

    @Operation(
            summary = "return trainer by id.",
            description = "return trainer by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return trainer by id.")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<TrainerResponse> getTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerService
                .findById(id);
        return ObjectResponse.of(trainer, TrainerResponse::new);
    }

    @Operation(
            summary = "create trainer.",
            description = "create trainer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create trainer")
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ObjectResponse<TrainerResponse> createTrainer(@RequestBody TrainerRequest trainerRequest) {
        try {
            Trainer trainer = new Trainer();
            trainer.setId(trainerRequest.getId());
            trainer.setName(trainerRequest.getTrainerName());
            trainerService.createTrainer(trainer);
            return ObjectResponse.of(trainer, TrainerResponse::new);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
