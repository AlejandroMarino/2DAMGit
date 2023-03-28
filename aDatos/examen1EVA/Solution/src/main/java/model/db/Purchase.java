package model.db;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Purchase {
    @XmlElement(name="id")
    private int id;
    private Client client;
    private LocalDate p_date;
    private double total_cost;
    private List<Purchases_items> items_purchased;
    private int paid;

    // a contructor without the items purchased
    public Purchase(int id, Client client, LocalDate p_date, double total_cost, int paid) {
        this.id = id;
        this.client = client;
        this.p_date = p_date;
        this.total_cost = total_cost;
        this.paid = paid;
    }


    public Purchase(Client client, LocalDate p_date, List<Purchases_items> items_purchased) {
        this.client = client;
        this.p_date = p_date;
        this.items_purchased = items_purchased;
    }


    public Purchase(double total_cost, int paid) {
        this.total_cost = total_cost;
        this.paid = paid;
    }

    public Purchase(double total_cost) {
        this.total_cost = total_cost;
    }

    public Purchase(int id_purchase, double total_cost) {
        this.id = id_purchase;
        this.total_cost = total_cost;
    }

    public Purchase(int id, Client client) {
        this.id = id;
        this.client = client;
    }
}
