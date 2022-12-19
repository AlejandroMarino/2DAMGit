package ui.common;

public enum Screens {

    PRINCIPAL ("/fxml/Principal.fxml"),
    LOGIN ("/fxml/Login.fxml"),
    NEWSPAPER("/fxml/Newspaper.fxml"),
    DELNEWSPAPER("/fxml/DelNewspaper.fxml"),
    ARTICLE("/fxml/Articles.fxml"),
    ADARTICLE("/fxml/AddArticles.fxml"),
    FILTERARTICLE("/fxml/FilterArticles.fxml");




    private String ruta;

    Screens(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
