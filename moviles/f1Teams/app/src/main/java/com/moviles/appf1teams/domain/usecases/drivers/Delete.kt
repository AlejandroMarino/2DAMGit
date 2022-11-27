package com.moviles.appf1teams.domain.usecases.drivers

import com.moviles.appf1teams.data.TeamRepository
import javax.inject.Inject

class Delete @Inject constructor(private val teamsRepository: TeamRepository) {

        suspend fun invoke(idDriver: Int ): Boolean {
            return try{
                teamsRepository.deleteDriver(idDriver)
                true
            }catch (e: Exception){
                false
            }
        }
}