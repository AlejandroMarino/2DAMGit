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
                @NamedQuery(name = HQLQueries.HQL_GET_ESTADOS_CONTRATO, query = "select sC.estado from SicarioContratoEntity sC where sC.contrato.id = :idContrato"),
                @NamedQuery(name = HQLQueries.HQL_GET_SICARIO_CONTRATO, query = "from SicarioContratoEntity sC where sC.contrato.id = :idContrato and sC.sicario.id = :idSicario"),
        }
)
public class SicarioContratoEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = Constants.ID_USUARIO_COLUMN)
    private UsuarioEntity sicario;
    @Id
    @ManyToOne
    @JoinColumn(name = Constants.ID_CONTRATO_COLUMN)
    private ContratoEntity contrato;
    @Column
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Column
    private String clave;

}