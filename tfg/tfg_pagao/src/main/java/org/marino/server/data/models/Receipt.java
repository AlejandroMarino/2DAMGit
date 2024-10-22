package org.marino.server.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    private int id;
    private String name;
    private String description;
    private int groupId;
    private List<Participation> participations = new ArrayList<>();

    public Receipt(int id, String name, String description, int groupId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groupId = groupId;
    }
}
