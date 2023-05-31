package domain.model.modelMongo;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Usuario {
    private String nombre;
    @Singular
    private List<Permiso> permisos;
}
