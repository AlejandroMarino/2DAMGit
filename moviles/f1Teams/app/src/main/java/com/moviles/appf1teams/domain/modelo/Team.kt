package com.moviles.appf1teams.domain.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    var name: String = "",
    var performance: Float = 0F,
    var tyre: Int = 0,
    var winner: Boolean = false
) : Parcelable
