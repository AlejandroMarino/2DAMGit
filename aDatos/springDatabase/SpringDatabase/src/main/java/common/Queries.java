package common;

public class Queries {
    public static final String GET_QUANTITY_OF_ITEMS_PER_TABLE = "SELECT t.id, t.table_number, t.number_of_seats, mi.name, SUM(oi.quantity) AS quantity " +
            "FROM orders o " +
            "INNER JOIN order_items oi ON oi.order_id = o.id " +
            "INNER JOIN menu_items mi ON mi.id = oi.menu_item_id " +
            "INNER Join tables t on o.table_id = t.id " +
            "GROUP BY t.id, mi.name";
    public static final String GET_CUSTOMER = "SELECT * FROM customers WHERE id = ?";
    public static final String GET_LOGIN = "SELECT * FROM login WHERE username = ?";
    public static final String GET_MENU_ITEM_OF_order_item = "SELECT * FROM menu_items WHERE id = ?";
    public static final String GET_MENU_ITEM_BY_NAME = "SELECT * FROM menu_items WHERE name = ?";
    public static final String UPDATE_MENU_ITEM = "UPDATE menu_items SET name = ?, description = ?, price = ? WHERE id = ?";
    public static final String GET_ALL_ORDER_ITEMS_FROM_ORDER = "SELECT * FROM order_items WHERE order_id = ?";
    public static final String GET_ALL_ORDERS = "SELECT * FROM orders";
    public static final String GET_ORDERS_PAID_OLDER_THAN_THIS_YEAR = "SELECT * FROM orders WHERE paid = ? " +
            "&& YEAR(order_date) < YEAR(CURDATE())";
    public static final String GET_LAST_ORDER_FROM_CUSTOMER = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC LIMIT 1";
    public static final String DELETE_ORDER_ITEMS_OF_ORDER = "DELETE FROM order_items WHERE order_id = ?";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
    public static final String GET_MOST_REQUESTED_TABLE = "SELECT t.id, t.table_number, t.number_of_seats, COUNT(o.id) AS total_orders " +
            "FROM tables t " +
            "INNER JOIN orders o ON o.table_id = t.id " +
            "WHERE YEAR(order_date) = YEAR(CURDATE()) " +
            "GROUP BY t.id " +
            "ORDER by count(o.id) DESC " +
            "LIMIT 1";
    public static final String GET_ITEMS_REQUESTED_BY_TABLE = "SELECT mi.name, SUM(oi.quantity) AS quantity " +
            "FROM orders o " +
            "INNER JOIN order_items oi ON oi.order_id = o.id " +
            "INNER JOIN menu_items mi ON mi.id = oi.menu_item_id " +
            "INNER Join tables t on o.table_id = t.id " +
            "WHERE t.id = ? " +
            "GROUP BY t.id, mi.name";
    public static final String GET_ALL_TABLES = "SELECT * FROM tables";
    public static final String GET_TABLE = "SELECT * FROM tables WHERE id = ?";
    public static final String ADD_LOGIN = "INSERT INTO login (username, password, role) VALUES (:username, :password, :role)";
    public static final String ADD_ORDER_ITEM = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (:order_id, :menu_item_id, :quantity)";
}
