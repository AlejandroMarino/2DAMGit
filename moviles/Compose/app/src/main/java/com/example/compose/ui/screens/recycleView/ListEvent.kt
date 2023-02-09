package com.example.compose.ui.screens.recycleView

sealed class ListEvent {
    object GetProducts : ListEvent()
}