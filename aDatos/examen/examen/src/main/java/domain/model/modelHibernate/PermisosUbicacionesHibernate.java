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
@jakarta.persistence.Table(name = "permisos_ubicaciones")
@NamedQueries(
        {
                @NamedQuery(name= Constants.HQL_GET_PERMISO_USUARIO_UBICACION, query = "select pU.permiso from PermisosUbicacionesHibernate pU WHERE pU.usuario.id = :idUs and pU.ubicacion.id = :idUb")
        }
)
public class PermisosUbicacionesHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private UbicacionHibernate ubicacion;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioHibernate usuario;
    @ManyToOne
    @JoinColumn(name = "id_permiso", nullable = false)
    private PermisosHibernate permiso;

    public PermisosUbicacionesHibernate(UbicacionHibernate ubicacion, UsuarioHibernate usuario) {
        this.ubicacion = ubicacion;
        this.usuario = usuario;
    }
}
