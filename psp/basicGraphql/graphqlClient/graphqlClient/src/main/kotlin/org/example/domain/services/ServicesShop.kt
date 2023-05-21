package org.example.domain.services

import org.example.data.dao.DaoShops
import org.example.domain.model.Shop

object ServicesShop {
    private val dS: DaoShops = DaoShops

    fun getAllShops() = dS.getAllShops()
    fun getShopsFilterByName(name: String) = dS.getShopsFilterByName(name)
    fun getShop(id: Int) = dS.getShop(id)
    fun addShop(name: String) = dS.addShop(name)
    fun updateShop(shop: Shop) = dS.updateShop(shop)
    fun deleteShop(id: Int) = dS.deleteShop(id)
}