package com.example.compose.ui.screens.list

sealed class ListEvent {
    object GetProducts : ListEvent()
}