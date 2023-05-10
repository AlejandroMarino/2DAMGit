package domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "menu_items")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_NAME_OF_ITEMS_ORDERED_BY_CUSTOMER", query = "select oi.menuItem.name from OrderItem oi where oi.order.customer.id = :id")
})
public class MenuItem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;
}
