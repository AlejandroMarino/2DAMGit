package com.example.servidorspring.spring.Controller;

import com.example.servidorspring.domain.services.ServicesGame;
import domain.models.Game;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse/games")
public class RestGamesController {

    private final ServicesGame sG;

    public RestGamesController(ServicesGame sG) {
        this.sG = sG;
    }

    @GetMapping
    public List<Game> getAll() {
        return sG.getAll();
    }

    @GetMapping("/{id}")
    public Game get(@PathVariable int id) {
        return sG.get(id);
    }

    @GetMapping("/filter_name")
    public List<Game> filterByName(@RequestParam String name) {
        return sG.filterByName(name);
    }

    @GetMapping("/filter_shop")
    public List<Game> filterByShop(@RequestParam int shop) {
        return sG.getAllOfShop(shop);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game add(@RequestBody Game game) {
        return sG.add(game);
    }

    @PutMapping()
    public Game update(@RequestBody Game game) {
        return sG.update(game);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam int id) {
        sG.delete(id);
    }
}
