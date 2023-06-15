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
@Table(name = "purchases_items")

@NamedQueries(
        {

        }
)
public class PurchasesItemsHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_purchase")
    private PurchaseHibernate purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item")
    private ItemHibernate item;

    @Column
    private int amount;


    public PurchasesItemsHibernate(ItemHibernate item, int amount) {
        this.item = item;
        this.amount = amount;
    }
}
