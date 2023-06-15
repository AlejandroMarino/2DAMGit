package domain.model.modelHibernate;

import common.Constants;
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
@Table(name = "purchases")

@NamedQueries(
        {
            @NamedQuery(name = Constants.HQL_GET_ALL_PURCHASES, query = "from PurchaseHibernate p")
        }
)
public class PurchaseHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private ClientHibernate client;

    @Column(name = "p_date")
    private LocalDate date;

    @Column(name = "total_cost")
    private double total;

    @Column
    private boolean paid;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PurchasesItemsHibernate> items;

    public PurchaseHibernate(ClientHibernate client, LocalDate date, double total, boolean paid, List<PurchasesItemsHibernate> items) {
        this.client = client;
        this.date = date;
        this.total = total;
        this.paid = paid;
        this.items = items;
    }

    @Override
    public String toString() {
        return "PurchaseHibernate{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", paid=" + paid +
                '}';
    }


    public String toStringWithItems() {
        return "PurchaseHibernate{" +
                "id=" + id +
                ", client=" + client +
                ", date=" + date +
                ", total=" + total +
                ", paid=" + paid +
                ", items=" + items +
                '}';
    }
}
