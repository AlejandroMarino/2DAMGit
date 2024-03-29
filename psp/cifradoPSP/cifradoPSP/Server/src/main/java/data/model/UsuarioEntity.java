package data.model;

import common.Constants;
import common.HQLQueries;
import domain.models.Tipo;
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
@Table(name = Constants.TABLE_NAME_USUARIOS)
@NamedQueries(
        {
                @NamedQuery(name = HQLQueries.HQL_GET_USUARIO_BY_ID, query = "from UsuarioEntity u where u.id = :id"),
                @NamedQuery(name = HQLQueries.HQL_GET_USUARIO_BY_NAME, query = "from UsuarioEntity u where u.nombre = :nombre"),
                @NamedQuery(name = HQLQueries.HQL_GET_SICARIOS_FILTER_HABILIDAD, query = "from UsuarioEntity u where u.tipo = :tipo and u.habilidad >= :habilidad"),
                @NamedQuery(name = HQLQueries.HQL_GET_SICARIOS_OF_CONTRATO, query = "select sC.sicario from SicarioContratoEntity sC where sC.contrato.id = :idContrato"),
        }
)
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;
    @Column
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column
    private String password;
    @Column
    private int habilidad;
    @Column
    private String clave;

}