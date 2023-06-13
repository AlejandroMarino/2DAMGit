package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Informe {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private List<String> roles;
}
