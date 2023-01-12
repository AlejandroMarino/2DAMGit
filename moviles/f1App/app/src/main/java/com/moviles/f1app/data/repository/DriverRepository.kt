package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.DriverDao
import com.moviles.f1app.data.modelo.toDriver
import com.moviles.f1app.data.modelo.toDriverEntity
import com.moviles.f1app.data.modelo.toDriverRaceCrossRef
import com.moviles.f1app.domain.modelo.Driver
import javax.inject.Inject

class DriverRepository @Inject constructor(private val driverDao: DriverDao) {

    suspend fun getDrivers() = driverDao.getDrivers().map { it.toDriver() }

    suspend fun getDriversOfTeam(idTeam: Int) =
        driverDao.getDriversOfTeam(idTeam).map { it.toDriver() }

    suspend fun getDriver(id: Int) = driverDao.getDriver(id).toDriver()

    suspend fun getDriverByName(name: String) = driverDao.getDriverByName(name).toDriver()

    suspend fun updateDriver(id: Int, name: String, number: Int, idTeam: Int) =
        driverDao.updateDriver(id, name, number, idTeam)

    suspend fun deleteDriver(driver: Driver) = driverDao.deleteDriver(
        driver.toDriverEntity(),
        driver.performances.toList().map { it.second.toDriverRaceCrossRef() })

    suspend fun addDriver(driver: Driver) = driverDao.addDriver(
        driver.toDriverEntity()
    )
}