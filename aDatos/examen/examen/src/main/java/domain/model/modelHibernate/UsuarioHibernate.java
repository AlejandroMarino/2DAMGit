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
@Table(name = "usuarios")

@NamedQueries(
        {
                @NamedQuery(name = Constants.HQL_GET_USUARIOSUBICACION, query = "select pU.usuario from PermisosUbicacionesHibernate pU where pU.ubicacion.id = :id"),
                @NamedQuery(name = Constants.HQL_GET_USUARIOS_PERMISOS_OBJETOS, query = "select pO.usuario from PermisosObjetosHibernate pO where pO.objeto.id = :id")
        }
)
public class UsuarioHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name= "fechaNacimiento")
    private String fechaNacimiento;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<PermisosObjetosHibernate> permisosObjetos;

    public UsuarioHibernate(int id) {
        this.id = id;
    }
}
