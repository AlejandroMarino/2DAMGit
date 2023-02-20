package com.example.compose2.ui.screens.list

sealed class ListEvent {
    object GetProducts : ListEvent()
}