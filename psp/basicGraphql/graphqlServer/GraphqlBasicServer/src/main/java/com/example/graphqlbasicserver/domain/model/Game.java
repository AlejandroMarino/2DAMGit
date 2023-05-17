package com.example.graphqlbasicserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Game {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int shopId;

    public Game(String name, String description, LocalDate releaseDate, int shopId) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.shopId = shopId;
    }
}
