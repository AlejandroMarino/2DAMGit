package domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "customers")

@NamedQueries(
        {
                @NamedQuery(name = "HQL_GET_ALL_CUSTOMERS", query = "from Customer c"),
                @NamedQuery(name = "HQL_GET_CUSTOMER", query = "from Customer c where c.id = :id")
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
}
