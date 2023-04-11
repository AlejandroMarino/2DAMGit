package cliente.ui.pantallas.principal;

import cliente.ui.common.BasePantallaController;
import cliente.ui.common.Pantallas;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalController implements Initializable {

    @FXML
    private MenuItem itemGames;
    @FXML
    private MenuItem itemShops;
    @FXML
    private Menu goTo;
    @FXML
    public BorderPane root;
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
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return panePantalla;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goShops();
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }

    private void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    public void goShops() {
        cargarPantalla(Pantallas.SHOPS);
    }

    public void goGames() {
        cargarPantalla(Pantallas.GAMES);
    }

    public void error(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
