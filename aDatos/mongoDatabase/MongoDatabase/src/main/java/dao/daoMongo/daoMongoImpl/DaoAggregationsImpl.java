package dao.daoMongo.daoMongoImpl;

import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoAggregations;
import domain.model.modelMongo.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class DaoAggregationsImpl implements DaoAggregations {

    private final Configuration config;

    @Inject
    public DaoAggregationsImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, String> getMostExpensiveItemDescription() {
        /*
        [
  {
    $sort: {
      price: -1,
    },
  },
  {
    $limit: 1,
  },
  {
    $project: {
      _id: 0,
      description: 1,
    },
  },
]*//*
Arrays.asList(new Document("$sort",
    new Document("price", -1L)),
    new Document("$limit", 1L),
    new Document("$project",
    new Document("_id", 0L)
            .append("description", 1L)))

         */
        MongoCollection<Document> coll = config.getDb().getCollection("menuItem");
        try {
            Document document = coll.aggregate(
                    Arrays.asList(
                            sort(descending("price")),
                            limit(1),
                            project(fields(excludeId(), include("description"))))
            ).first();
            return Either.right(document.getString("description"));
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getOrdersOfCustomer(Customer customer) {
        /*
        [
  {
    $match: {
      _id: ObjectId("646b7f8ccec16b6dc30193b8"),
    },
  },
  {
    $project: {
      _id: 0,
      name: 1,
      numberOfSeats:
        "$orders.table.numberOfSeats",
    },
  },
]*//*
Arrays.asList(new Document("$match",
    new Document("_id",
    new ObjectId("646b7f8ccec16b6dc30193b8"))),
    new Document("$project",
    new Document("_id", 0L)
            .append("name", 1L)
            .append("numberOfSeats", "$orders.table.numberOfSeats")))
         */
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        try {
            Document document = coll.aggregate(
                    Arrays.asList(
                            match(eq("_id", customer.get_id())),
                            project(fields(excludeId(), computed("name", 1),
                                    computed("numberOfSeats", "$orders.table.numberOfSeats"))
                            )
                    )).first();
            return Either.right(document.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getNumberOfItemsOfOrders() {
        /*
[
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $group: {
      _id: {
        date: "$orders.date",
        total: "$orders.total",
        paid: "$orders.paid",
      },
      items: {
        $sum: "$orders.orderItems.quantity",
      },
    },
  },
  {
    $project: {
      _id: 0,
    },
  },
]*//*
Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$group",
    new Document("_id",
    new Document("date", "$orders.date")
                .append("total", "$orders.total")
                .append("paid", "$orders.paid"))
            .append("items",
    new Document("$sum", "$orders.orderItems.quantity"))),
    new Document("$project",
    new Document("_id", 0L)))
         */
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        try {
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            group(
                                    and(
                                            eq("date", "$orders.date"),
                                            eq("total", "$orders.total"),
                                            eq("paid", "$orders.paid")
                                    ), sum("items", "$orders.orderItems.quantity")
                            ),
                            project(fields(excludeId()))
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getNameOfCustomerWithSteak() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $match: {
      "orders.orderItems.name": "Steak",
    },
  },
  {
    $project: {
      _id: 0,
      name: 1,
    },
  },
]*//*
Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$match",
    new Document("orders.orderItems.name", "Steak")),
    new Document("$project",
    new Document("_id", 0L)
            .append("name", 1L)))
         */
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        try {
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            match(eq("orders.orderItems.name", "Steak")),
                            project(fields(excludeId(), include("name")))
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getAverageNumberOfItemsPerOrder() {
        /*
[
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $group: {
      _id: {
        date: "$orders.date",
        total: "$orders.total",
        paid: "$orders.paid",
      },
      items: {
        $sum: "$orders.orderItems.quantity",
      },
    },
  },
  {
    $group: {
      _id: null,
      avg: {
        $avg: "$items",
      },
    },
  },
  {
    $project: {
      _id: 0,
      avg: 1,
    },
  },
]
*//*
Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$group",
    new Document("_id",
    new Document("date", "$orders.date")
                .append("total", "$orders.total")
                .append("paid", "$orders.paid"))
            .append("items",
    new Document("$sum", "$orders.orderItems.quantity"))),
    new Document("$group",
    new Document("_id",
    new BsonNull())
            .append("avg",
    new Document("$avg", "$items"))),
    new Document("$project",
    new Document("_id", 0L)
            .append("avg", 1L)))
         */
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        try {
            Document document = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            group(
                                    and(
                                            eq("date", "$orders.date"),
                                            eq("total", "$orders.total"),
                                            eq("paid", "$orders.paid")
                                    ), sum("items", "$orders.orderItems.quantity")
                            ),
                            group(null, avg("avg", "$items")),
                            project(fields(excludeId(), include("avg")))
                    )
            ).first();
            return Either.right(document.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getMostRequestedItem() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $group: {
      _id: {
        id: "$orders.orderItems.menuItemId",
        name: "$orders.orderItems.name",
      },
      count: {
        $sum: "$orders.orderItems.quantity",
      },
    },
  },
  {
    $sort: {
      count: -1,
    },
  },
  {
    $limit: 1,
  },
  {
    $project: {
      name: "$orders.orderItems.name",
      count: 1,
    },
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$group",
    new Document("_id",
    new Document("id", "$orders.orderItems.menuItemId")
                .append("name", "$orders.orderItems.name"))
            .append("count",
    new Document("$sum", "$orders.orderItems.quantity"))),
    new Document("$sort",
    new Document("count", -1L)),
    new Document("$limit", 1L),
    new Document("$project",
    new Document("name", "$orders.orderItems.name")
            .append("count", 1L)))
         */
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        try {
            Document document = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            group(
                                    and(
                                            eq("id", "$orders.orderItems.menuItemId"),
                                            eq("name", "$orders.orderItems.name")
                                    ), sum("count", "$orders.orderItems.quantity")
                            ),
                            sort(descending("count")),
                            limit(1),
                            project(fields(include("name", "count")))
                    )
            ).first();
            return Either.right(document.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getNumberOfEachItemOrderedByCustomer(Customer customer) {
        /*
        [
  {
    $match: {
      _id: ObjectId("646b7f8ccec16b6dc30193b8"),
    },
  },
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $group: {
      _id: "$orders.orderItems.name",
      quantity: {
        $sum: "$orders.orderItems.quantity",
      },
    },
  },
]
         *//*
         Arrays.asList(new Document("$match",
    new Document("_id",
    new ObjectId("646b7f8ccec16b6dc30193b8"))),
    new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$group",
    new Document("_id", "$orders.orderItems.name")
            .append("quantity",
    new Document("$sum", "$orders.orderItems.quantity"))))
    */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            match(eq("_id", customer.get_id())),
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            group("$orders.orderItems.name", sum("quantity", "$orders.orderItems.quantity"))
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getMostRequestedTable() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $group: {
      _id: "$orders.table.number",
      count: {
        $sum: 1,
      },
    },
  },
  {
    $sort: {
      count: -1,
    },
  },
  {
    $limit: 1,
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$group",
    new Document("_id", "$orders.table.number")
            .append("count",
    new Document("$sum", 1L))),
    new Document("$sort",
    new Document("count", -1L)),
    new Document("$limit", 1L))
         */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            Document document = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            group("$orders.table.number", sum("count", 1)),
                            sort(descending("count")),
                            limit(1)
                    )
            ).first();
            return Either.right(document.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getMostRequestedTablePerCustomer() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $group: {
      _id: {
        customer: "$_id",
        table: "$orders.table.number",
      },
      count: {
        $sum: 1,
      },
    },
  },
  {
    $sort: {
      count: -1,
    },
  },
  {
    $group: {
      _id: "$_id.customer",
      mesa: {
        $max: "$_id.table",
      },
    },
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$group",
    new Document("_id",
    new Document("customer", "$_id")
                .append("table", "$orders.table.number"))
            .append("count",
    new Document("$sum", 1L))),
    new Document("$sort",
    new Document("count", -1L)),
    new Document("$group",
    new Document("_id", "$_id.customer")
            .append("mesa",
    new Document("$max", "$_id.table"))))
         */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            group(
                                    and(
                                            eq("customer", "$_id"),
                                            eq("table", "$orders.table.number")
                                    ), sum("count", 1)
                            ),
                            sort(descending("count")),
                            group("$_id.customer", max("mesa", "$_id.table"))
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getItemsNotRequestedMoreThan1() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $group: {
      _id: {
        id: "$orders.orderItems.menuItemId",
        name: "$orders.orderItems.name",
      },
      times: {
        $sum: 1,
      },
    },
  },
  {
    $match: {
      times: {
        $lt: 2,
      },
    },
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$group",
    new Document("_id",
    new Document("id", "$orders.orderItems.menuItemId")
                .append("name", "$orders.orderItems.name"))
            .append("times",
    new Document("$sum", 1L))),
    new Document("$match",
    new Document("times",
    new Document("$lt", 2L))))
         */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            group(
                                    and(
                                            eq("id", "$orders.orderItems.menuItemId"),
                                            eq("name", "$orders.orderItems.name")
                                    ), sum("times", 1)
                            ),
                            match(lt("times", 2))
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<Document>> getPricePaidForEachOrder() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $unwind: {
      path: "$orders.orderItems",
    },
  },
  {
    $lookup: {
      from: "menuItem",
      localField: "orders.orderItems.menuItemId",
      foreignField: "_id",
      as: "items",
    },
  },
  {
    $unwind: {
      path: "$items",
    },
  },
  {
    $group: {
      _id: {
        date: "$orders.date",
        total: "$orders.total",
        paid: "$orders.paid",
      },
      orderPrice: {
        $sum: {
          $multiply: [
            "$items.price",
            "$orders.orderItems.quantity",
          ],
        },
      },
      items: {
        $push: "$items",
      },
    },
  },
]
         */
        try{
        MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
        List<Document> documents = coll.aggregate(
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$unwind",
    new Document("path", "$orders.orderItems")),
    new Document("$lookup",
    new Document("from", "menuItem")
            .append("localField", "orders.orderItems.menuItemId")
            .append("foreignField", "_id")
            .append("as", "items")),
    new Document("$unwind",
    new Document("path", "$items")),
    new Document("$group",
    new Document("_id",
    new Document("date", "$orders.date")
                .append("total", "$orders.total")
                .append("paid", "$orders.paid"))
            .append("orderPrice",
    new Document("$sum",
    new Document("$multiply", Arrays.asList("$items.price", "$orders.orderItems.quantity"))))
            .append("items",
    new Document("$push", "$items"))))
    ).into(new ArrayList<>());
        return Either.right(documents);
    } catch (Exception e) {
        return Either.left(-1);
        }
/*
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            unwind("$orders.orderItems"),
                            lookup("menuItem", "orders.orderItems.menuItemId", "_id", "items"),
                            unwind("$items"),
                            group(
                                    and(
                                            eq("date", "$orders.date"),
                                            eq("total", "$orders.total"),
                                            eq("paid", "$orders.paid")
                                    ),
                                    sum("orderPrice",
                                            multiply(("$orders.orderItems.quantity"),("$items.price"))
                                    ),
                                    push("items", "$items")
                            )
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
        */
    }

    @Override
    public Either<Integer, String> getCustomerSpentMost() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $group: {
      _id: {
        id: "$_id",
        name: "$name",
      },
      spent: {
        $sum: "$orders.total",
      },
    },
  },
  {
    $sort: {
      spent: -1,
    },
  },
  {
    $limit: 1,
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$group",
    new Document("_id",
    new Document("id", "$_id")
                .append("name", "$name"))
            .append("spent",
    new Document("$sum", "$orders.total"))),
    new Document("$sort",
    new Document("spent", -1L)),
    new Document("$limit", 1L))
         */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            group(
                                    and(
                                            eq("id", "$_id"),
                                            eq("name", "$name")
                                    ),
                                    sum("spent", "$orders.total")
                            ),
                            sort(descending("spent")),
                            limit(1)
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, String> getTotalNotPaid() {
        /*
        [
  {
    $unwind: {
      path: "$orders",
    },
  },
  {
    $match: {
      "orders.paid": false,
    },
  },
  {
    $group: {
      _id: [],
      totalNotPaid: {
        $sum: "$orders.total",
      },
    },
  },
  {
    $project: {
      _id: 0,
    },
  },
]
         *//*
         Arrays.asList(new Document("$unwind",
    new Document("path", "$orders")),
    new Document("$match",
    new Document("orders.paid", false)),
    new Document("$group",
    new Document("_id", Arrays.asList())
            .append("totalNotPaid",
    new Document("$sum", "$orders.total"))),
    new Document("$project",
    new Document("_id", 0L)))
         */
        try {
            MongoCollection<Document> coll = config.getDb().getCollection("restaurant");
            List<Document> documents = coll.aggregate(
                    Arrays.asList(
                            unwind("$orders"),
                            match(eq("orders.paid", false)),
                            group(
                                    new Document(),
                                    sum("totalNotPaid", "$orders.total")
                            ),
                            project(excludeId())
                    )
            ).into(new ArrayList<>());
            return Either.right(documents.toString());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }
}
