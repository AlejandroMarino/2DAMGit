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
public class Purchase {
    private ObjectId _id;
    private String date;
    private Client client;
    @Singular
    private List<Item> items;

    public Purchase(String date, Client client, List<Item> items) {
        this.date = date;
        this.client = client;
        this.items = items;
    }

    public Purchase(ObjectId _id, List<Item> items) {
        this._id = _id;
        this.items = items;
    }
}
