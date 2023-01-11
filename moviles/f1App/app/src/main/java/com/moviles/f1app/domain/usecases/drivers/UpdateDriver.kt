package com.moviles.f1app.domain.usecases.drivers

import com.moviles.f1app.data.repository.DriverRepository
import com.moviles.f1app.domain.modelo.Driver
import javax.inject.Inject

class UpdateDriver @Inject constructor(private val driverRepository: DriverRepository) {

    suspend fun invoke(driver: Driver): Boolean {
        return try {
            driverRepository.updateDriver(driver.id, driver.name, driver.number, driver.idTeam)
            true
        } catch (e: Exception) {
            false
        }
    }
}
