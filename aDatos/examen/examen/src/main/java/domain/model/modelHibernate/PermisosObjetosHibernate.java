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
@Table(name = "permisos_objetos")

@NamedQueries(
        {
            @NamedQuery(name= Constants.HQL_GET_PERMISO_USUARIO_OBJETO, query = "select pO.permiso from PermisosObjetosHibernate pO WHERE pO.usuario.id = :idU and pO.objeto.id = :idO")
        }
)
public class PermisosObjetosHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioHibernate usuario;
    @ManyToOne
    @JoinColumn(name = "id_objeto", nullable = false)
    private ObjetoHibernate objeto;
    @ManyToOne
    @JoinColumn(name = "id_permiso", nullable = false)
    private PermisosHibernate permiso;

    public PermisosObjetosHibernate(UsuarioHibernate usuario, ObjetoHibernate objeto, PermisosHibernate permiso) {
        this.usuario = usuario;
        this.objeto = objeto;
        this.permiso = permiso;
    }

    public PermisosObjetosHibernate(UsuarioHibernate usuario, ObjetoHibernate objeto) {
        this.usuario = usuario;
        this.objeto = objeto;
    }
}
