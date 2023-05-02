package cliente.ui.common;

public enum Pantallas {

    PRINCIPAL("/fxml/Principal.fxml"),
    SHOPS("/fxml/Shops.fxml"),
    GAMES("/fxml/Games.fxml"),
    LOGIN("/fxml/Login.fxml"),
    REGISTER("/fxml/Register.fxml");

    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
