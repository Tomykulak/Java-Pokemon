package cz.mendelu.ea.pokemon.domain.pokemon;

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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/pokemons")
@Validated
@Tag(
        name = "Pokemons",
        description = "Pokemon endpoint."
)
public class PokemonController {

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
    private final PokemonService pokemonService;

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "returns all pokemons.",
            description = "returns all pokemons"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retuns all pokemons.")
    })
    public ArrayResponse<PokemonResponse> getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.findAll();
        return ArrayResponse.of(
                pokemons,
                PokemonResponse::new
        );
    }

    @Operation(
            summary = "return pokemon by id.",
            description = "return pokemon by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return pokemon by id.")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<PokemonResponse> getPokemonById(@PathVariable Long id) {
        Pokemon pokemon = pokemonService
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return ObjectResponse.of(pokemon, PokemonResponse::new);
    }


    @Operation(
            summary = "create pokemon.",
            description = "create pokemon, only accessible by authenticated SUPERADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create pokemon, only accessible by authenticated SUPERADMIN")
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ObjectResponse<PokemonResponse> createPokemon(@RequestBody PokemonRequest pokemonRequest) {
        try {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(pokemonRequest.getId());
            pokemon.setName(pokemonRequest.getName());
            pokemon.setType1(pokemonRequest.getType1());
            pokemon.setType2(pokemonRequest.getType2());
            pokemon.setTotal(pokemonRequest.getTotal());
            pokemon.setHp(pokemonRequest.getHp());
            pokemon.setAttack(pokemonRequest.getAttack());
            pokemon.setDefense(pokemonRequest.getDefense());
            pokemon.setSp_attack(pokemonRequest.getSp_attack());
            pokemon.setSp_defense(pokemonRequest.getSp_defense());
            pokemon.setSpeed(pokemonRequest.getSpeed());
            pokemon.setGeneration(pokemonRequest.getGeneration());
            pokemon.setLegendary(pokemonRequest.isLegendary());
            pokemonService.createPokemon(pokemon);
            return ObjectResponse.of(pokemon, PokemonResponse::new);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed to create pokemon", e);
        }
    }

    @Operation(
            summary = "update pokemon by id.",
            description = "update pokemon  by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update pokemon  by id")
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<PokemonResponse> updatePokemon(@PathVariable Long id, @RequestBody @Valid PokemonRequest pokemonRequest) {
        Pokemon pokemon = pokemonService
                .findById(id)
                .orElseThrow(NotFoundException::new);

        pokemonRequest.toPokemon(pokemon);
        pokemonService.updatePokemon(id, pokemon);
        return ObjectResponse.of(pokemon, PokemonResponse::new);
    }

    @Operation(
            summary = "delete pokemon.",
            description = "delete pokemon"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete pokemon")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deletePokemon(@PathVariable Long id) {
        Pokemon pokemon = pokemonService
                .findById(id)
                .orElseThrow(NotFoundException::new);
        pokemonService.deletePokemon(id);
    }
}
