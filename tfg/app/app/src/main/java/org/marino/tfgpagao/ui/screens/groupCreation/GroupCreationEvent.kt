package org.marino.tfgpagao.ui.screens.groupCreation

sealed class GroupCreationEvent {
    object ErrorCatch : GroupCreationEvent()
    class AddGroup(val goGroupList: () -> Unit) : GroupCreationEvent()
    class NameChanged(val newName: String) : GroupCreationEvent()
    class DescriptionChanged(val newDescription: String) : GroupCreationEvent()
    object MemberAdded : GroupCreationEvent()
    class MemberDeleted(val index: Int) : GroupCreationEvent()
    class MemberNameChanged(val index: Int, val newName: String) : GroupCreationEvent()
}