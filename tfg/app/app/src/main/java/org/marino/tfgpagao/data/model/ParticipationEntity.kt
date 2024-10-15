package org.marino.tfgpagao.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],

            ),
        ForeignKey(
            entity = ReceiptEntity::class,
            parentColumns = ["id"],
            childColumns = ["receiptId"],
        )
    ],
    primaryKeys = ["memberId", "receiptId"],
    tableName = "participation"
)
data class ParticipationEntity (
    val memberId: Int,
    val receiptId: Int,
    val description: String?,
    val pays: Double?,
    val debts: Double?,
)