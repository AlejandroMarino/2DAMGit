package com.example.graphqlbasicserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Shop {
    private int id;
    private String name;

    public Shop(String name) {
        this.name = name;
    }
}
