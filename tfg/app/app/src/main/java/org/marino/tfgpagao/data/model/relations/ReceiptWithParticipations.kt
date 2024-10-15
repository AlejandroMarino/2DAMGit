package org.marino.tfgpagao.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import org.marino.tfgpagao.data.model.ParticipationEntity
import org.marino.tfgpagao.data.model.ReceiptEntity

data class ReceiptWithParticipations(
    @Embedded val receipt: ReceiptEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "receiptId"
    )
    val participations: List<ParticipationEntity>
)