package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "article")

@NamedQueries({ @NamedQuery(name= "GetAllArticles", query="from Article") })
public class Article {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name_article")
    private String name;
    @Column(name = "id_type")
    private int type;
    @Column(name = "id_newspaper")
    private int idNewspaper;
}
