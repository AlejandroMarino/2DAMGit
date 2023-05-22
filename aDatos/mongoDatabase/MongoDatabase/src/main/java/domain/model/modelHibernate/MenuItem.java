package domain.model.modelHibernate;

import common.Constants;
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
        @NamedQuery(name = Constants.HQL_GET_ALL_MENU_ITEMS, query = "from MenuItem mi"),
        @NamedQuery(name = Constants.HQL_GET_NAME_OF_ITEMS_ORDERED_BY_CUSTOMER, query = "select DISTINCT oi.menuItem.name from OrderItem oi where oi.order.customer.id = :id"),
        @NamedQuery(name = Constants.HQL_GET_MENU_ITEM_BY_NAME, query = "from MenuItem mi where lower(mi.name) = :name"),
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

    public MenuItem(String name) {
        this.name = name;
    }

    public MenuItem(int id) {
        this.id = id;
    }

    public MenuItem(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
