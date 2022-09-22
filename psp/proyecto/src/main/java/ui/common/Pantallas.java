package ui.common;

public enum Pantallas {

    PRINCIPAL ("/fxml/Principal.fxml"),
    LOGIN ("/fxml/Login.fxml"),
    NEWSPAPER("/fxml/Newspaper.fxml");


    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
