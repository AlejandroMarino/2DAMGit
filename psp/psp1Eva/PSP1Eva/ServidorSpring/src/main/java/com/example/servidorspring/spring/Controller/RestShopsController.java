package com.example.servidorspring.spring.Controller;

import com.example.servidorspring.domain.services.ServicesShop;
import domain.models.Shop;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse/shops")
public class RestShopsController {

    private ServicesShop sS;

    public RestShopsController(ServicesShop sS) {
        this.sS = sS;
    }

    @GetMapping
    public List<Shop> getAll() {
        return sS.getAll();
    }

    @GetMapping("/{id}")
    public Shop get(@PathVariable int id) {
        return sS.get(id);
    }

    @GetMapping("/filter")
    public List<Shop> filterByName(@RequestParam String name) {
        return sS.filterByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shop add(@RequestBody Shop shop) {
        return sS.add(shop);
    }

    @PutMapping()
    public Shop update(@RequestBody Shop shop) {
        return sS.update(shop);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam int id) {
        sS.delete(id);
    }

}
