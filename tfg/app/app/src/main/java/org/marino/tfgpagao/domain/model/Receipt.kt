package org.marino.tfgpagao.domain.model

data class Receipt (
    val id: Int,
    val name: String,
    val descriction: String?,
    val participations: List<Participation>?,
)