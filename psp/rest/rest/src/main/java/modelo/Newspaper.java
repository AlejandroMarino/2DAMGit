package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Newspaper {
    private int id;
    private String name;
    private LocalDate releaseDate;
}
