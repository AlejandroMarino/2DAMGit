package data.model;

import common.Constants;
import common.HQLQueries;
import domain.models.Estado;
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
@Table(name = Constants.TABLE_NAME_SICARIOS_CONTRATOS)
@NamedQueries(
        {
                @NamedQuery(name = HQLQueries.HQL_GET_ESTADO_SICARIO_CONTRATO, query = "select sC.estado from SicariosContratosEntity sC where sC.sicario.id = :idSicario and sC.contrato.id = :idContrato"),
                @NamedQuery(name = HQLQueries.HQL_GET_ESTADOS_CONTRATO, query = "select estado from SicariosContratosEntity sC where sC.contrato.id = :idContrato"),
        }
)
public class SicariosContratosEntity {
    @EmbeddedId
    private SicariosContratosId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = Constants.ID_USUARIO_COLUMN, insertable = false, updatable = false)
    private UsuarioEntity sicario;
    @ManyToOne
    @MapsId("idComtrato")
    @JoinColumn(name = Constants.ID_CONTRATO_COLUMN, insertable = false, updatable = false)
    private ContratoEntity contrato;
    @Column
    private Estado estado;
    @Column
    private String clave;

    public SicariosContratosEntity(UsuarioEntity sicario, ContratoEntity contrato, Estado estado, String clave) {
        this.sicario = sicario;
        this.contrato = contrato;
        this.estado = estado;
        this.clave = clave;
    }
}