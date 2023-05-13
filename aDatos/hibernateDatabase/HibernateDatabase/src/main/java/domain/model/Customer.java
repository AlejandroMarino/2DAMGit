package domain.model;

import common.Constants;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "customers")

@NamedQueries(
        {
                @NamedQuery(name = Constants.HQL_GET_ALL_CUSTOMERS, query = "from Customer c"),
                @NamedQuery(name = Constants.HQL_GET_CUSTOMER, query = "from Customer c where c.id = :id"),
                @NamedQuery(name = Constants.HQL_GET_CUSTOMER_SPENT_MORE_MONEY, query = "select oi.order.customer, sum(oi.quantity * oi.menuItem.price)" +
                        " from OrderItem oi group by oi.order.customer order by sum(oi.quantity * oi.menuItem.price) desc limit 1"),
        }
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column
    private String email;
    @Column
    private String phone;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String toStringWithOrders() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders.toString() +
                '}';
    }

    public Customer(int id) {
        this.id = id;
    }

    public Customer(String firstname, String lastname, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int id, String firstname, String lastname, String email, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public String toStringWithMoneySpent(double moneySpent) {
        return "Name= " + firstname + ' ' + lastname +
                ", money spent=" + moneySpent +
                '}';
    }
}
