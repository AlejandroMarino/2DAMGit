package domain.model.modelHibernate;

import common.Constants;
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
@Table(name = "items")

@NamedQueries(
        {
                @NamedQuery(name = Constants.HQL_GET_ITEM_BY_NAME, query = "from ItemHibernate i where i.name = :name"),
                @NamedQuery(name = Constants.HQL_GET_ALL_ITEMS_OF_CLIENT, query = "select pI.item from PurchasesItemsHibernate pI where pI.purchase.client.name = :name")
        }
)
public class ItemHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private double price;

    @Override
    public String toString() {
        return "ItemHibernate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
