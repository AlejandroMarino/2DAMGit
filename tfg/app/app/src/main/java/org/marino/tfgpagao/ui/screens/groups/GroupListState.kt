package org.marino.tfgpagao.ui.screens.groups

import org.marino.tfgpagao.domain.model.Group

data class GroupListState (
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)