package org.marino.tfgpagao.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.marino.tfgpagao.common.Constants
import org.marino.tfgpagao.data.local.dao.GroupDao
import org.marino.tfgpagao.data.local.dao.MemberDao
import org.marino.tfgpagao.data.local.dao.ParticipationDao
import org.marino.tfgpagao.data.local.dao.ReceiptDao
import org.marino.tfgpagao.data.model.GroupEntity
import org.marino.tfgpagao.data.model.MemberEntity
import org.marino.tfgpagao.data.model.ParticipationEntity
import org.marino.tfgpagao.data.model.ReceiptEntity


@Database(entities = [GroupEntity::class, MemberEntity::class, ReceiptEntity::class, ParticipationEntity::class], version = Constants.VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun participationDao(): ParticipationDao
}