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
    public static final String ACTIVATE_USER = "UPDATE user SET activated = true WHERE activation_code = ?";
    public static final String GET_ROLES_OF_USER = "SELECT name FROM role r INNER JOIN userRole uR on r.id = uR.role_id WHERE uR.username = ?";
    public static final String GET_USER = "SELECT * FROM user WHERE username = ?";
    public static final String ADD_USER = "INSERT INTO user (username, password, activated, email, activation_code) VALUES (?, ?, ?, ?, ?)";
}
