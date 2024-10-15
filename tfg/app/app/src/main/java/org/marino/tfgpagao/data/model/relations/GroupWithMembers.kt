package org.marino.tfgpagao.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import org.marino.tfgpagao.data.model.GroupEntity
import org.marino.tfgpagao.data.model.MemberEntity

data class GroupWithMembers (
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val members: List<MemberEntity>
)