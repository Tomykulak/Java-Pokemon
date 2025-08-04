package cz.mendelu.ea.pokemon.domain.trainer;

import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;
import cz.mendelu.ea.pokemon.utils.response.ArrayResponse;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@Validated
@Tag(name = "Trainers", description = "Trainer endpoint.")
public class TrainerController {

    private final TrainerService trainerService;
    private final ArenaService arenaService;

    @Autowired
    public TrainerController(TrainerService trainerService, ArenaService arenaService) {
        this.trainerService = trainerService;
        this.arenaService = arenaService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(summary = "returns all trainers.", description = "returns all trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns all trainers.")
    })
    public ArrayResponse<TrainerResponse> getAllTrainers() {
        List<Trainer> trainers = trainerService.findAll();
        return ArrayResponse.of(trainers, TrainerResponse::new);
    }

    @Operation(summary = "return trainer by id.", description = "return trainer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return trainer by id.")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<TrainerResponse> getTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerService.findById(id);
        if (trainer == null) {
            throw new NotFoundException();
        }
        return ObjectResponse.of(trainer, TrainerResponse::new);
    }

    @Operation(summary = "create trainer.", description = "create trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "create trainer")
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ObjectResponse<TrainerResponse> createTrainer(@RequestBody @Valid TrainerRequest trainerRequest) {
        Trainer trainer = trainerRequest.toTrainer(new Trainer(), arenaService);
        trainerService.createTrainer(trainer);
        return ObjectResponse.of(trainer, TrainerResponse::new);
    }

    @Operation(summary = "update trainer.", description = "update trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "update trainer")
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<TrainerResponse> updateTrainer(@PathVariable Long id, @RequestBody @Valid TrainerRequest trainerRequest) {
        Trainer trainer = trainerService.findById(id);
        if (trainer == null) {
            throw new NotFoundException();
        }
        trainerRequest.toTrainer(trainer, arenaService);
        trainerService.createTrainer(trainer);
        return ObjectResponse.of(trainer, TrainerResponse::new);
    }

    @Operation(summary = "delete trainer.", description = "delete trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete trainer")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteTrainer(@PathVariable Long id) {
        Trainer trainer = trainerService.findById(id);
        if (trainer == null) {
            throw new NotFoundException();
        }
        trainerService.deleteTrainer(id);
    }
}
