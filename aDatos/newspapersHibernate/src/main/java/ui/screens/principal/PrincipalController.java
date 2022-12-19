package ui.screens.principal;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;
import ui.common.BaseScreenController;
import ui.common.Screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalController implements Initializable {
    @FXML
    private Menu art;
    @FXML
    private Menu news;
    @FXML
    private MenuItem delNewspapers;
    @FXML
    private MenuItem addArti;
    @FXML
    private MenuItem filterArti;
    @FXML
    private MenuItem itemArti;
    @FXML
    private MenuItem itemNews;
    @FXML
    private MenuItem itemLogout;
    @FXML
    private Menu goTo;
    @FXML
    private BorderPane root;

    Instance<Object> instance;

    private final Alert alert;

    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }

    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return panePantalla;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goLogin();
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }

    private void cargarPantalla(Screens pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    public void goLogin() {
        cargarPantalla(Screens.LOGIN);
        goTo.setVisible(false);
        itemLogout.setVisible(false);
        itemNews.setVisible(false);
        itemArti.setVisible(false);
        news.setVisible(false);
        art.setVisible(false);
    }

    public void goNewspaper() {
        cargarPantalla(Screens.NEWSPAPER);
        itemLogout.setVisible(true);
        goTo.setVisible(true);
        news.setVisible(true);
        itemNews.setVisible(false);
        itemArti.setVisible(true);
        delNewspapers.setVisible(true);
        addArti.setVisible(true);
        filterArti.setVisible(true);
        art.setVisible(true);
    }

    public void goArti() {
        cargarPantalla(Screens.ARTICLE);
        itemLogout.setVisible(true);
        goTo.setVisible(true);
        news.setVisible(true);
        itemNews.setVisible(true);
        delNewspapers.setVisible(true);
        itemArti.setVisible(false);
        addArti.setVisible(true);
        filterArti.setVisible(true);
        art.setVisible(true);
    }

    public void goDelNewspaper() {
        cargarPantalla(Screens.DELNEWSPAPER);
        itemLogout.setVisible(true);
        goTo.setVisible(true);
        news.setVisible(true);
        itemNews.setVisible(true);
        delNewspapers.setVisible(false);
        itemArti.setVisible(true);
        addArti.setVisible(true);
        filterArti.setVisible(true);
        art.setVisible(true);
    }

    public void goAddArti() {
        cargarPantalla(Screens.ADARTICLE);
        itemLogout.setVisible(true);
        goTo.setVisible(true);
        news.setVisible(true);
        itemNews.setVisible(true);
        delNewspapers.setVisible(true);
        itemArti.setVisible(true);
        addArti.setVisible(false);
        filterArti.setVisible(true);
        art.setVisible(true);
    }

    public void goFilterArti() {
        cargarPantalla(Screens.FILTERARTICLE);
        itemLogout.setVisible(true);
        goTo.setVisible(true);
        news.setVisible(true);
        itemNews.setVisible(true);
        delNewspapers.setVisible(true);
        itemArti.setVisible(true);
        addArti.setVisible(true);
        filterArti.setVisible(false);
        art.setVisible(true);
    }

    public void error(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public boolean confirmacion(String mensaje) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
        return alert.getResult().getText().equals("Aceptar");
    }
}
