package com.moviles.appf1teams.utils

import android.content.Context
import androidx.annotation.StringRes

class StringProvider (private val context: Context) {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

}
