package dao.common;


public class SQLQueries {

    public static final String SELECT_FROM_ITEMS_WHERE_ID = "SELECT price FROM items WHERE id = ?";

    public static final String UPDATE_BALANCE_FROM_CLIENT = "UPDATE clients SET balance = balance - ? WHERE id = ?";
    public static final String UPDATE_PAID_FROM_PURCHASES = "UPDATE purchases SET paid = ? WHERE id = ?";

    public static final String SELECT_TOP_PURCHASED_ITEM = "select i.name from purchases_items inner join items i on purchases_items.id_item = i.id group by id_item order by sum(amount) desc limit 1";

    public static final String DELETE_FROM_PURCHASE_ITEMS_WHERE_ID_CLIENT = "delete from purchases_items where id_purchase in (select purchases.id from purchases inner join clients c on purchases.id_client = c.id where c.id = ?)";
    public static final String DELETE_FROM_PURCHASES_WHERE_ID_CLIENT = "DELETE FROM purchases WHERE id_client = ?";
    public static final String DELETE_FROM_CLIENTS_WHERE_ID_CLIENT = "DELETE FROM clients WHERE id = ?";

    public static final String SELECT_FROM_PURCHASED_ITEMS = "select distinct i.price, id_purchase, amount, p.total_cost from purchases_items INNER JOIN purchases JOIN purchases p on purchases_items.id_purchase = p.id " +
            "inner join items i on purchases_items.id_item = i.id where purchases_items.id = ?";

    public static final String UPDATE_TOTAL_COST="UPDATE purchases SET total_cost = total_cost+? WHERE id = ?";
    public static final String SELECT_ITEM_ID_FROM_PURCHASES_ITEMS = "SELECT id_client, id_purchase, id_item FROM purchases_items inner join purchases p on purchases_items.id_purchase = p.id";
    public static final String SELECT_ID_NAME_BALANCE_FROM_CLIENTS = "SELECT * FROM clients";

    public static final String SELECT_PURCHASES = "SELECT id, total_cost, id_client FROM purchases";

    public static final String SELECT_TOTAL_COST_FROM_PURCHASES = "SELECT total_cost FROM purchases";

    private SQLQueries() {
    }
    public static final String INSERT_INTO_PURCHASES= "INSERT INTO purchases (id_client, p_date, total_cost, paid) VALUES (?, ?, 0, 0)";

    public static final String SELECT_THINGS_FROM_PURCHASES = "SELECT id_client, total_cost, paid, c.balance from purchases JOIN clients c on purchases.id_client = c.id WHERE purchases.id = ?";

    public static final String INSERT_INTO_PURCHASES_ITEMS = "INSERT INTO purchases_items (id_purchase, id_item, amount) VALUES (?, ?, ?)";

    public static final String UPDATE_PURCHASES_SET_TOTALPRICE= "UPDATE purchases SET total_cost = ? where id = ?";

    public static final String UPDATE_PURCHASES_ITEMS_SET_AMOUNT= "UPDATE purchases_items SET amount = ? where id = ?";

    public static final String DELETE_FROM_CLIENTS_BY_ID = "DELETE FROM clients WHERE id = ?";

}
