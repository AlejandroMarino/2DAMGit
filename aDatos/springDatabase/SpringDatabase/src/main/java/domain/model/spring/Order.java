package domain.model.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private int tableId;
    private int customerId;
    private LocalDate orderDate;
    private boolean paid;

    public Order(int tableId, int customerId, LocalDate orderDate) {
        this.tableId = tableId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.paid = false;
    }
}
