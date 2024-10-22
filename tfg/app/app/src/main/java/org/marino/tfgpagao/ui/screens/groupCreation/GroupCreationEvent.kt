package org.marino.tfgpagao.ui.screens.groupCreation

sealed class GroupCreationEvent {
    object ErrorCatch: GroupCreationEvent()
    object AddGroup: GroupCreationEvent()
}