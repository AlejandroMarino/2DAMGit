package domain.model.xml;

import java.time.LocalDate;

public class Order {
    private int id;
    private LocalDate date;
    private double total;
    private Items items;
}
