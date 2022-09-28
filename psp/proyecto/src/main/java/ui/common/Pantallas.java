package ui.common;

public enum Pantallas {

    PRINCIPAL("/fxml/Principal.fxml"),
    INICIO("/fxml/Inicio.fxml"),
    ALLFISH("/fxml/AllFish.fxml"),
    INFOFISH("/fxml/InfoFish.fxml");


    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
