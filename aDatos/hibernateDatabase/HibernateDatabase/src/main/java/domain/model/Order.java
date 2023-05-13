package domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@jakarta.persistence.Table(name = "orders")
@NamedQueries(
        {
                @NamedQuery(name = common.Constants.HQL_GET_ORDERS_OF_CUSTOMER,
                        query = "from Order o where o.customer.id = :id"),
                @NamedQuery(name = common.Constants.HQL_GET_ALL_ORDERS_WITH_NUMBER_OF_ITEMS,
                        query = "select oi.order, sum(oi.quantity) from OrderItem oi group by oi.order"),
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private Table table;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_date")
    private LocalDate date;

    @Column
    private boolean paid;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;

    public Order(int id) {
        this.id = id;
    }

    public Order(Table table, Customer customer, LocalDate date, boolean paid, List<OrderItem> orderItems) {
        this.table = table;
        this.customer = customer;
        this.date = date;
        this.paid = paid;
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table.toString() +
                ", date=" + date +
                ", paid=" + paid +
                '}';
    }

    public String toStringWithOrderItems() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", customer=" + customer +
                ", date=" + date +
                ", paid=" + paid +
                ", orderItems=" + orderItems +
                '}';
    }

    public String toStringWithNumberOfItems(long numberOfItems) {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", paid=" + paid +
                ", number of items=" + numberOfItems +
                '}';
    }

    public String toStringWithCustomerNameAndOrderItems() {
        return "Order{" +
                "id=" + id +
                ", number of seats=" + table.getNumberOfSeats() +
                ", customer name=" + customer.getFirstname() + " " + customer.getLastname() +
                ", date=" + date +
                ", paid=" + paid +
                '}';
    }
}
