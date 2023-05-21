package org.example.ui.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.example.domain.model.Shop
import org.example.domain.services.ServicesShop
import org.example.ui.common.BaseScreenController
import org.example.utils.Trither
import java.net.URL
import java.util.*

class ShopController : Initializable, BaseScreenController() {
    @FXML
    private lateinit var textName: TextField

    @FXML
    private lateinit var textSearch: TextField

    @FXML
    private lateinit var listShops: ListView<Shop>

    private val sS = ServicesShop


    override fun principalCargado() {
        clearFilters()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        listShops.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                textName.text = newValue.name
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun search() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val text = textSearch.text
            if (text != null && text.isNotBlank()) {
                listShops.items.clear()
                sS.getShopsFilterByName(text).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            trither.data?.let { listShops.items.addAll(it) }
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message!!)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("El campo de busqueda esta vacio")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun add() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val text = textName.text
            if (text != null && text.isNotBlank()) {
                sS.addShop(text).collect { trither ->
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
                            getPrincipalController()!!.showAlertError(trither.message!!)
                        }
                    }
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("El campo de nombre esta vacio")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun update() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val text = textName.text
            if (text != null && text.isNotBlank()) {
                val shop = listShops.selectionModel.selectedItem
                if (shop != null) {
                    val s = Shop(shop.id, text)
                    sS.updateShop(s).collect { trither ->
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
                                getPrincipalController()!!.showAlertError(trither.message!!)
                            }
                        }
                    }
                } else {
                    getPrincipalController()!!.showLoading(false)
                    getPrincipalController()!!.showAlertError("No se ha seleccionado ninguna tienda")
                }
            } else {
                getPrincipalController()!!.showLoading(false)
                getPrincipalController()!!.showAlertError("El campo de nombre esta vacio")
            }
            getPrincipalController()!!.showLoading(false)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            val shop = listShops.selectionModel.selectedItem
            if (shop != null) {
                sS.deleteShop(shop.id).collect { trither ->
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
                            getPrincipalController()!!.showAlertError(trither.message!!)
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
    fun clearFilters() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            listShops.items.clear()
            sS.getAllShops().collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let { listShops.items.addAll(it) }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message!!)
                    }
                }
            }
            getPrincipalController()!!.showLoading(false)
        }
    }


}