package com.moviles.appf1teams.domain.modelo

import android.os.Parcelable
import com.moviles.appf1teams.domain.common.Constantes
import kotlinx.parcelize.Parcelize




@Parcelize
data class Team(
    var id: Int? = null,
    var name: String = Constantes.defaultName,
    var performance: Float = Constantes.defaultPerformance,
    var tyre: Int = Constantes.defaultTyre,
    var winner: Boolean = Constantes.defaultWinner
) : Parcelable


