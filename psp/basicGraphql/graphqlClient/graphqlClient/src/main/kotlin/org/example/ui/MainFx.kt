package org.example.ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import org.example.ui.common.Screens
import org.example.ui.controllers.PrincipalController

class MainFx : Application() {

    override fun start(primaryStage: Stage?) {
        try {
            val loaderMenu = FXMLLoader(
                javaClass.getResource(Screens.PRINCIPAL.ruta)
            )

            val root: BorderPane? = loaderMenu.load()
            val controller: PrincipalController = loaderMenu.getController()
            controller.setStage(primaryStage)
            val scene = Scene(root)
            primaryStage!!.title = "Warehouse"
            primaryStage.scene = scene
            primaryStage.isResizable = false
            primaryStage.minWidth = 1000.0
            primaryStage.minHeight = 700.0
            primaryStage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
    fun main(args: Array<String>) {
        Application.launch(MainFx::class.java, *args)
    }
