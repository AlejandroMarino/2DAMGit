package org.marino.tfgpagao.ui.screens.groupCreation

import org.marino.tfgpagao.domain.model.Group

data class GroupCreationState(
    val group: Group = Group(
        id = 0,
        name = "",
        description = null,
        receipts = emptyList(),
        members = emptyList()
    ),
    val error: String? = null,
    val isLoading: Boolean = false,
)