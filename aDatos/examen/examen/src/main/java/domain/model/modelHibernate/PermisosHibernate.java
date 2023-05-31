package domain.model.modelHibernate;

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
@Table(name = "permisos")

@NamedQueries(
        {

        }
)
public class PermisosHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String descripcion;

    public PermisosHibernate(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PermisosHibernate{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
