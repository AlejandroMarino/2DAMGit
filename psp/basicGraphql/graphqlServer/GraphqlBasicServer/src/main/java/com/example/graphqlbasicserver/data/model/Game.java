package com.example.graphqlbasicserver.data.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Game {
    private String id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private String shopId;
}
