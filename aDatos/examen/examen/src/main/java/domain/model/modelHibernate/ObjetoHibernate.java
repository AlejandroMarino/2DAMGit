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
@Table(name = "objetos")

@NamedQueries(
        {
                @NamedQuery(name = Constants.HQL_GET_OBJETO, query = "from ObjetoHibernate o where o.id = :id"),
                @NamedQuery(name = Constants.HQL_GET_OBJETOS_UBICACION, query = "from ObjetoHibernate o where o.ubicacion.id = :id ")
        }
)
public class ObjetoHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private String marca;
    @Column
    private String tipo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_ubicacion")
    private UbicacionHibernate ubicacion;

    public ObjetoHibernate(int id) {
        Id = id;
    }
}
