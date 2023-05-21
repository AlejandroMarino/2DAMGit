package org.example.ui.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Alert
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.WindowEvent
import lombok.extern.log4j.Log4j2
import org.example.ui.common.BaseScreenController
import org.example.ui.common.Screens
import java.io.IOException
import kotlin.system.exitProcess

@Log4j2
class PrincipalController {

    @FXML
    var root: BorderPane? = null

    private var alert: Alert = Alert(Alert.AlertType.NONE)

    private var primaryStage: Stage? = null

    fun showAlertError(mensaje: String?) {
        alert.alertType = Alert.AlertType.ERROR
        alert.contentText = mensaje
        alert.isResizable = true
        alert.showAndWait()
    }

    fun showAlertInformation(message: String?) {
        val infoAlert = Alert(Alert.AlertType.INFORMATION)
        infoAlert.contentText = message
        infoAlert.showAndWait()
    }

    fun showLoading(show: Boolean = true) {
        if (show) {
            root!!.cursor = javafx.scene.Cursor.WAIT
        } else {
            root!!.cursor = javafx.scene.Cursor.DEFAULT
        }
    }

    private fun cargarPantalla(ruta: String): Pane? {
        var panePantalla: Pane? = null
        try {
            val fxmlLoader = FXMLLoader()
            panePantalla = fxmlLoader.load(javaClass.getResourceAsStream(ruta))
            val pantallaController: BaseScreenController = fxmlLoader.getController()
            pantallaController.setPrincipalController(this)
            pantallaController.principalCargado()
        } catch (e: IOException) {
            showAlertError("Error al cargar la pantalla: $ruta ($e)")
        }
        cambioPantalla(panePantalla)
        return panePantalla
    }

    fun setStage(stage: Stage?) {
        primaryStage = stage
        primaryStage!!.addEventFilter(
            WindowEvent.WINDOW_CLOSE_REQUEST
        ) {
            primaryStage!!.close()
            exitProcess(0)
        }
    }

    private fun cambioPantalla(pantallaNueva: Pane?) {
        root!!.center = pantallaNueva
    }

    fun initialize() {
        goShops()
    }

    private fun cargarPantalla(pantalla: Screens) {
        cambioPantalla(cargarPantalla(pantalla.ruta))
    }

    fun goShops() {
        cargarPantalla(Screens.SHOPS)
    }

    fun goGames() {
        cargarPantalla(Screens.GAMES)
    }

}