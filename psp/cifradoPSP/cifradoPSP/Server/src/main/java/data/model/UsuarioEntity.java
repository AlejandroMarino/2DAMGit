package data.model;

import domain.models.Tipo;
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

        }
)
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;
    @Column
    private Tipo tipo;
    @Column
    private String password;
    @Column
    private int habilidad;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ContratoEntity> sicariosContratos;
}