package data.model;

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
@Table(name = "sicarios_contratos")
@NamedQueries(
        {

        }
)
public class SicariosContratosEntity {
    @Id
    @Column(name = "id_sicario")
    private int idSicario;
    @Id
    @Column(name = "id_contrato")
    private int idContrato;
    @Column
    private Estado estado;
    @Column
    private int clave;
}