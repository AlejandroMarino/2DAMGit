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

    @Id
    @ManyToOne
    @JoinColumn(name = Constants.ID_USUARIO_COLUMN, insertable = false, updatable = false)
    private UsuarioEntity sicario;
    @Id
    @ManyToOne
    @JoinColumn(name = Constants.ID_CONTRATO_COLUMN, insertable = false, updatable = false)
    private ContratoEntity contrato;
    @Column
    private Estado estado;
    @Column
    private String clave;

}