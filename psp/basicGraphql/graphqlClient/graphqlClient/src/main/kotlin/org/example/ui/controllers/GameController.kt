package org.example.ui.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.example.domain.model.Game
import org.example.domain.model.Shop
import org.example.domain.services.ServicesGame
import org.example.domain.services.ServicesShop
import org.example.ui.common.BaseScreenController
import org.example.utils.Trither
import java.net.URL
import java.util.*

class GameController : Initializable, BaseScreenController() {
    @FXML
    private lateinit var textSearch: TextField

    @FXML
    private lateinit var choiceShops: ComboBox<Shop>

    @FXML
    private lateinit var textDescription: TextArea

    @FXML
    private lateinit var datePicker: DatePicker

    @FXML
    private lateinit var textName: TextField

    @FXML
    private lateinit var colDescription: TableColumn<String, Game>

    @FXML
    private lateinit var colDate: TableColumn<String, Game>

    @FXML
    private lateinit var colName: TableColumn<Game, String>

    @FXML
    private lateinit var tableGames: TableView<Game>

    private val sG = ServicesGame

    private val sS = ServicesShop

    override fun principalCargado() {
        clearFilters()
        getShops()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colName.cellValueFactory = PropertyValueFactory("name")
        colDescription.cellValueFactory = PropertyValueFactory("description")
        colDate.cellValueFactory = PropertyValueFactory("releaseDate")

        tableGames.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                textName.text = newValue.name
                textDescription.text = newValue.description
                datePicker.value = newValue.releaseDate
                choiceShops.selectionModel.select(newValue.shop)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun add() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val name = textName.text
            val description = textDescription.text
            val date = datePicker.value
            val shop = choiceShops.selectionModel.selectedItem
            if (name != null && name.isNotBlank() && shop != null) {
                val g = Game(name = name, description = description, releaseDate = date, shop = shop)
                sG.addGame(g).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            clearFilters()
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("El nombre no puede estar vacío")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val selectedItem = tableGames.selectionModel.selectedItem
            if (selectedItem != null) {
                sG.deleteGame(selectedItem.id).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            clearFilters()
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("No se ha seleccionado ningún juego")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun update() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val name = textName.text
            val description = textDescription.text
            val date = datePicker.value
            val shop = choiceShops.selectionModel.selectedItem
            val selectedItem = tableGames.selectionModel.selectedItem
            if (name != null && name.isNotBlank() && shop != null && selectedItem != null) {
                val g = Game(
                    id = selectedItem.id,
                    name = name,
                    description = description,
                    releaseDate = date,
                    shop = shop
                )
                sG.updateGame(g).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            clearFilters()
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("No se ha seleccionado ningún juego o hay campos vacíos")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchByName() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val text = textSearch.text
            if (text != null && text.isNotBlank()) {
                tableGames.items.clear()
                sG.getGamesFilterByName(text).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            trither.data?.let { tableGames.items.addAll(it) }
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("No se ha introducido ningún nombre")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun filterByShop() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val shop = choiceShops.selectionModel.selectedItem
            if (shop != null) {
                tableGames.items.clear()
                sG.getGamesOfShop(shop.id).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            trither.data?.let { tableGames.items.addAll(it) }
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("No se ha seleccionado ninguna tienda")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getShops() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            sS.getAllShops().collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let { choiceShops.items.addAll(it) }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message)
                    }
                }
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun clearFilters() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            tableGames.items.clear()
            sG.getAllGames().collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let { tableGames.items.addAll(it) }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message)
                    }
                }
            }
            getPrincipalController()!!.showLoading(false)
        }
    }
}
