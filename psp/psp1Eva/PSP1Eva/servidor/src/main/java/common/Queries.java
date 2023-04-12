package common;

public class Queries {

    public static final String GET_ALL_GAMES = "SELECT * FROM game";
    public static final String FILTER_GAMES_BY_SHOP = "SELECT * FROM game WHERE shop_id = ?";
    public static final String GET_GAME = "SELECT * FROM game WHERE id = ?";
    public static final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
    public static final String UPDATE_GAME = "UPDATE game SET name = ?, description = ?, release_date = ?, shop_id = ? WHERE id = ?";
    public static final String UPDATE_SHOP = "UPDATE shop SET name = ? WHERE id = ?";
    public static final String DELETE_SHOP = "DELETE FROM shop WHERE id = ?";
    public static final String DELETE_GAME_BY_SHOP = "DELETE FROM game WHERE shop_id = ?";
    public static final String GET_SHOP = "SELECT * FROM shop WHERE id = ?";
    public static final String GET_ALL_SHOPS = "SELECT * FROM shop";
}
