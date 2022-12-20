package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "reader")

@NamedQueries({ @NamedQuery(name = "GetAllReaders", query="from Reader") })
public class Reader {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name_reader")
    private String name;
    @Column(name = "birth_reader")
    private LocalDate birthDate;
}
