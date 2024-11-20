package org.marino.tfgpagao.ui.screens.groupCreation

import org.marino.tfgpagao.ui.model.MemberVO

data class GroupCreationState(
    val name: String = "",
    val description: String = "",
    val members: List<MemberVO> = listOf(MemberVO("", false)),
    val error: String? = null,
    val isLoading: Boolean = false,
)