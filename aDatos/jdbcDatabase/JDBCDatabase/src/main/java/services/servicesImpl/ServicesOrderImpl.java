package services.servicesImpl;

import dao.*;
import domain.model.*;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ServicesOrderImpl implements ServicesOrder {

    private final DaoOrder dO;

    private final DaoCustomer dC;

    private final DaoTable dT;

    private final DaoMenuItem dMI;

    private final DaoOrderItem dOI;

    @Inject
    public ServicesOrderImpl(DaoOrder dO, DaoCustomer dC, DaoTable dT, DaoMenuItem dMI, DaoOrderItem dOI) {
        this.dO = dO;
        this.dC = dC;
        this.dT = dT;
        this.dMI = dMI;
        this.dOI = dOI;
    }


    @Override
    public Either<String, Map<Order, Double>> getAllOrdersOfCustomer(int customerId) {
        Either<Integer, Map<Order, Double>> result = dO.getAll(customerId);
        if (result.isLeft()) {
            return Either.left("Error while getting all orders");
        } else if (result.get().isEmpty()) {
            return Either.left("There are no orders for this customer");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Either<String, Void> addOrder(int customerId, int tableId, String name1, int quantity1, String name2, int quantity2) {
        Either<Integer, Customer> customer = dC.get(customerId);
        if (customer.isLeft()) {
            return Either.left("Customer not found");
        } else {
            Either<Integer, Table> table = dT.get(tableId);
            if (table.isLeft()) {
                return Either.left("Table not found");
            } else {
                Either<Integer, MenuItem> item1 = dMI.get(name1);
                if (item1.isLeft()) {
                    return Either.left("The first menu item was not found");
                } else {
                    Either<Integer, MenuItem> item2 = dMI.get(name2);
                    if (item2.isLeft()) {
                        return Either.left("The second menu item was not found");
                    } else {
                        Order order = new Order(table.get().getId(), customer.get().getId(), LocalDate.now());
                        Either<Integer, Order> result = dO.save(order);
                        if (result.isLeft()) {
                            return Either.left("Error while adding order");
                        } else {
                            OrderItem orderItem1 = new OrderItem(result.get().getId(), item1.get().getId(), quantity1);
                            OrderItem orderItem2 = new OrderItem(result.get().getId(), item2.get().getId(), quantity2);
                            Either<Integer, Void> result1 = dOI.save(orderItem1);
                            if (result1.isLeft()) {
                                return Either.left("Error while adding first order item");
                            } else {
                                Either<Integer, Void> result2 = dOI.save(orderItem2);
                                if (result2.isLeft()) {
                                    return Either.left("Error while adding second order item");
                                } else {
                                    return Either.right(null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Either<String, Void> editOrder(int customerId, int orderId, int tableId, String name1, int quantity1, String name2, int quantity2) {
        Either<Integer, Order> order = dO.get(orderId);
        if (order.isLeft()) {
            return Either.left("Order not found");
        } else if (order.get().getCustomerId() != customerId) {
            return Either.left("This order does not belong to this customer");
        } else {
            Either<Integer, Table> table = dT.get(tableId);
            if (table.isLeft()) {
                return Either.left("Table not found");
            } else {
                Either<Integer, MenuItem> item1 = dMI.get(name1);
                if (item1.isLeft()) {
                    return Either.left("The first menu item was not found");
                } else {
                    Either<Integer, MenuItem> item2 = dMI.get(name2);
                    if (item2.isLeft()) {
                        return Either.left("The second menu item was not found");
                    } else {
                        Either<Integer, List<OrderItem>> orderItems = dOI.getAll(orderId);
                        if (orderItems.isLeft()) {
                            return Either.left("Error while getting order items");
                        } else {
                            Either<Integer, Void> result1 = dOI.delete(orderItems.get().get(0));
                            if (result1.isLeft()) {
                                return Either.left("Error while deleting first order item");
                            } else {
                                Either<Integer, Void> result2 = dOI.delete(orderItems.get().get(1));
                                if (result2.isLeft()) {
                                    return Either.left("Error while deleting second order item");
                                } else {
                                    Order order1 = new Order(orderId, table.get().getId(), customerId, LocalDate.now());
                                    Either<Integer, Void> result3 = dO.update(order1);
                                    if (result3.isLeft()) {
                                        return Either.left("Error while updating order");
                                    } else {
                                        OrderItem orderItem1 = new OrderItem(orderId, item1.get().getId(), quantity1);
                                        OrderItem orderItem2 = new OrderItem(orderId, item2.get().getId(), quantity2);
                                        Either<Integer, Void> result4 = dOI.save(orderItem1);
                                        if (result4.isLeft()) {
                                            return Either.left("Error while adding first order item");
                                        } else {
                                            Either<Integer, Void> result5 = dOI.save(orderItem2);
                                            if (result5.isLeft()) {
                                                return Either.left("Error while adding second order item");
                                            } else {
                                                return Either.right(null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
