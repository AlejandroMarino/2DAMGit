package org.example.domain.model.modelHibernate;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UsuarioHibernate {
    private int id;
    private String nombre;
    private String fechaNacimiento;
}
