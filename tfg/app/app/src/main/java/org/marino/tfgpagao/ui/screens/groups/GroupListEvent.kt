package org.marino.tfgpagao.ui.screens.groups

sealed class GroupListEvent {
    object ErrorCatch: GroupListEvent()
    object GetGroups : GroupListEvent()
}