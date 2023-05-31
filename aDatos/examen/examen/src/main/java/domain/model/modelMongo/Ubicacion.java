package domain.model.modelMongo;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ubicacion {
    private ObjectId _id;
    private String nombre;
    private String edificio;
    @Singular
    private List<Usuario> usuarios;

}
