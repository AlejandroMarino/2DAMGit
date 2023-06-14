package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arma {

        private int id;
        private String descripcion;
        private int serieId;

        public Arma(int id, String descripcion) {
                this.id = id;
                this.descripcion = descripcion;
        }
}
