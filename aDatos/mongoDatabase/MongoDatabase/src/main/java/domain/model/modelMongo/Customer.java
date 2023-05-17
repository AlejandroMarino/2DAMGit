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
public class Customer {
    private ObjectId _id;
    private String name;
    @Singular
    private List<Order> orders;

}
