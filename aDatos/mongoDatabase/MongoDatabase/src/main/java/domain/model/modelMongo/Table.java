package domain.model.modelMongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Table {
    private int number;
    private int numberOfSeats;
}
