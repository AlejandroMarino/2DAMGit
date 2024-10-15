package org.marino.server.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    private int memberId;
    private int receiptId;
    private String description;
    private double pays;
    private double debts;
}
