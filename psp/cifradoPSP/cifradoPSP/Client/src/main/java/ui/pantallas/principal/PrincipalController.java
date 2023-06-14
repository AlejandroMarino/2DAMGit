package ui.pantallas.principal;

import domain.models.Contrato;
import domain.models.Usuario;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;
import ui.common.BasePantallaController;
import ui.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalController implements Initializable {
    @FXML
    public BorderPane root;
    Instance<Object> instance;
    private final Alert alert;

    private Usuario usuarioActual;

    private Contrato contratoActual;

    private KeyStore keyStore;

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public Contrato getContratoActual() {
        return contratoActual;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

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
        goLogin();
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }

    private void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }


    public void goLogin() {
        cargarPantalla(Pantallas.LOGIN);
    }

    public void goRegister() {
        cargarPantalla(Pantallas.REGISTER);
    }

    public void goContratista(Usuario usuario, KeyStore ks) {
        usuarioActual = usuario;
        keyStore = ks;
        cargarPantalla(Pantallas.CONTRATISTA);
    }

    public void volverContratista() {
        cargarPantalla(Pantallas.CONTRATISTA);
    }

    public void goCrearContrato() {
        cargarPantalla(Pantallas.CREARCONTRATO);
    }

    public void goElegirSicarios(Contrato contrato) {
        contratoActual = contrato;
        cargarPantalla(Pantallas.ELEGIRSICARIOS);
    }

    public void goSicario(Usuario usuario, KeyStore ks) {
        usuarioActual = usuario;
        keyStore = ks;
        cargarPantalla(Pantallas.SICARIO);
    }


    public void error(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void message(String mensaje) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
