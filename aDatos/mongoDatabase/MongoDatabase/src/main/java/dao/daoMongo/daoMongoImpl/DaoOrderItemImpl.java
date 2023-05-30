package dao.daoMongo.daoMongoImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoOrderItem;
import domain.model.modelMongo.MenuItem;
import domain.model.modelMongo.Order;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoOrderItemImpl implements DaoOrderItem {

    private final Configuration config;

    private final Gson gson;

    @Inject
    public DaoOrderItemImpl(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll(Order order) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            List<OrderItem> orderItems = new ArrayList<>();
            Document document = restaurantColl.find(new Document("orders.date", order.getDate())
                            .append("orders.total", order.getTotal()).append("orders.paid", order.isPaid()))
                    .projection(new Document("_id", 0).append("orders.date", 1).append("orders.total", 1)
                            .append("orders.paid", 1).append("orders.orderItems", 1)).first();
            if (document == null) {
                return Either.left(-2);
            } else {
                List<Document> orders = (List<Document>) document.get("orders");
                Document o = orders.stream().filter(ord -> {
                    Order order1 = gson.fromJson(ord.toJson(), Order.class);
                    return order1.getDate().equals(order.getDate()) && Objects.equals(order1.getTotal(), order.getTotal()) && order1.isPaid() == order.isPaid();
                }).findFirst().get();
                List<Document> oItems = (List<Document>) o.get("orderItems");
                for (Document oI : oItems) {
                    OrderItem orderItem = gson.fromJson(oI.toJson(), OrderItem.class);
                    orderItems.add(orderItem);
                }
                return Either.right(orderItems);
            }
        } catch (
                Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll(MenuItem menuItem) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            List<OrderItem> orderItems = new ArrayList<>();
            List<Document> documents = restaurantColl.find(new Document("orders.orderItems.menuItemId", menuItem.get_id()))
                    .projection(new Document("_id", 0).append("orders.orderItems", 1)).into(new ArrayList<>());
            if (documents.isEmpty()) {
                return Either.left(-2);
            } else {
                documents.stream().flatMap(document -> {
                    List<Document> orders = (List<Document>) document.get("orders");
                    return orders.stream();
                }).flatMap(document -> {
                    List<Document> orderItems1 = (List<Document>) document.get("orderItems");
                    return orderItems1.stream();
                }).filter(document -> {
                    OrderItem orderItem = gson.fromJson(document.toJson(), OrderItem.class);
                    return orderItem.getMenuItemId() == (menuItem.get_id());
                }).forEach(document -> {
                    OrderItem orderItem = gson.fromJson(document.toJson(), OrderItem.class);
                    orderItems.add(orderItem);
                });
            }
            return Either.right(orderItems);
        } catch (
                Exception e) {
            return Either.left(-1);
        }
    }
}

