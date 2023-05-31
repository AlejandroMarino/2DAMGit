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
@Table(name = "ubicaciones")

@NamedQueries(
        {
                @NamedQuery(name = Constants.HQL_GET_ALL_UBICACIONES, query = "from UbicacionHibernate u")
        }
)
public class UbicacionHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;
    @Column
    private String edificio;
    @Column
    private String planta;
    @Column
    private String puesto;
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<PermisosUbicacionesHibernate> permisosUbicacion;
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<ObjetoHibernate> objetos;

    @Override
    public String toString() {
        return "UbicacionHibernate{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edificio='" + edificio + '\'' +
                ", planta='" + planta + '\'' +
                ", puesto='" + puesto + '\'' +
                '}';
    }
}
