package cz.mendelu.ea.pokemon.domain.pokemon;

import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;
import cz.mendelu.ea.pokemon.utils.response.ArrayResponse;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
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
public class PokemonController {

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
    private final PokemonService pokemonService;

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<PokemonResponse> getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.findAll();
        return ArrayResponse.of(
                pokemons,
                PokemonResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<PokemonResponse> getPokemonById(@PathVariable Long id) {
        Pokemon pokemon = pokemonService
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return ObjectResponse.of(pokemon, PokemonResponse::new);
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public PokemonResponse createPokemon(@RequestBody PokemonRequest pokemonRequest) {
        try {
            return pokemonService.createPokemon(pokemonRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to create pokemon", e);
        }
    }
}
