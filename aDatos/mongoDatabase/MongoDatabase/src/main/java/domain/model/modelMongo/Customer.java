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

    public Customer(String name, List<Order> orders) {
        this.name = name;
        this.orders = orders;
    }

    public Customer(ObjectId _id, List<Order> orders) {
        this._id = _id;
        this.orders = orders;
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(ObjectId _id) {
        this._id = _id;
    }

    public Customer(ObjectId _id, String name) {
        this._id = _id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }

    public String toStringWithOrders() {
        return "Customer{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
