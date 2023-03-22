package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@ToString
public class Orders {
    private int id;
    private int tableId;
    private int customerId;
    private LocalDate orderDate;
    private double total;
}
