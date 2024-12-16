package com.craftworks.peerreview.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppTheme {
    var current by mutableStateOf(Theme.Dark)

    fun set(theme: Theme){
        current = theme
    }
}

enum class Theme {
    Light, Dark
}