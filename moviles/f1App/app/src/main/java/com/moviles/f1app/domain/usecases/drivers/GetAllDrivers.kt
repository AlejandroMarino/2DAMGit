package com.moviles.f1app.domain.usecases.drivers

import com.moviles.f1app.data.repository.DriverRepository
import javax.inject.Inject

class GetAllDrivers @Inject constructor(private val driverRepository: DriverRepository) {
    suspend fun invoke() = driverRepository.getDrivers()
}