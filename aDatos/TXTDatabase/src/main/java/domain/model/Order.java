package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Order {
    private int id;
    private int tableId;
    private int customerId;
    private LocalDate orderDate;
    private double total;

    public Order(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.tableId = Integer.parseInt(parts[1]);
        this.customerId = Integer.parseInt(parts[2]);
        this.orderDate = LocalDate.parse(parts[3]);
        this.total = Double.parseDouble(parts[4]);
    }

    public String toString(){
        return id + ";" + tableId + ";" + customerId + ";" + orderDate + ";" + total;
    }
}
