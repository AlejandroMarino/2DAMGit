package data.model;

import common.Constants;
import common.HQLQueries;
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
@Table(name = Constants.TABLE_NAME_CONTRATOS)
@NamedQueries(
        {
                @NamedQuery(name = HQLQueries.HQL_GET_CONTRATO_BY_ID, query = "from ContratoEntity  c where c.id = :idContrato"),
                @NamedQuery(name = HQLQueries.HQL_GET_CONTRATOS_OF_USUARIO, query= "from ContratoEntity c where c.usuario.id = :idUsuario"),
                @NamedQuery(name = HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO, query = "select sC.contrato from SicarioContratoEntity sC where sC.sicario.id = :idSicario"),
                @NamedQuery(name = HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO_FILTER_ESTADO, query = "select sC.contrato from SicarioContratoEntity sC where sC.sicario.id = :idSicario and sC.estado = :estado"),
        }
)
public class ContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String detalle;
    @Column
    private String clave;
    @Column
    private int habilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.ID_USUARIO_COLUMN, nullable = false)
    private UsuarioEntity usuario;
}