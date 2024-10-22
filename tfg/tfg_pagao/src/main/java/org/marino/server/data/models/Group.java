package org.marino.server.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private int id;
    private String name;
    private String description;
    private List<Member> members = new ArrayList<>();

    public Group(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
