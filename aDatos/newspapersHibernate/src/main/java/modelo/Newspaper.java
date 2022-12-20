package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "newspaper")

@NamedQueries({ @NamedQuery(name = "NewspapersGetAll", query="from Newspaper") })
public class Newspaper {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name_newspaper")
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}
