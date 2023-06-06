package ui.common;

public enum Pantallas {

    PRINCIPAL("/fxml/Principal.fxml"),
    LOGIN("/fxml/Login.fxml"),
    REGISTER("/fxml/Register.fxml"),
    CONTRATISTA("/fxml/Contratista.fxml"),
    CREARCONTRATO("/fxml/CrearContrato.fxml"),
    ELEGIRSICARIOS("/fxml/ElegirSicarios.fxml"),
    SICARIO("/fxml/Sicario.fxml");

    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
