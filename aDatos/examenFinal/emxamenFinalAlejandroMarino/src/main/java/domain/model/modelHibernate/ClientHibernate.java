package domain.model.modelHibernate;

import common.Constants;
import jakarta.persistence.*;
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
@Table(name = "clients")

@NamedQueries(
        {
            @NamedQuery(name = Constants.HQL_GET_CLIENT_BY_NAME, query = "from ClientHibernate c where c.name = :name"),
        }
)
public class ClientHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @Column
    private double balance;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<PurchaseHibernate> purchases;

    public ClientHibernate(String name) {
        this.name = name;
    }

    public ClientHibernate(int id) {
        this.id = id;
    }
}
