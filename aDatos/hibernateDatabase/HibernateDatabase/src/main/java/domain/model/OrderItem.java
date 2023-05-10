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
@Table(name = "order_items")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_ORDER_ITEMS", query = "from OrderItem oi"),
        @NamedQuery(name = "HQL_GET_ALL_ORDER_ITEMS_Of_ORDER", query = "from OrderItem oi WHERE oi.order.id = :id"),
        @NamedQuery(name = "HQL_GET_ORDER_ITEM", query = "from OrderItem oi where oi.id = :id")
})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column
    private int quantity;
}
