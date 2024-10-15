package org.marino.tfgpagao.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.marino.tfgpagao.common.Constants
import org.marino.tfgpagao.data.local.AppDatabase
import org.marino.tfgpagao.data.local.dao.GroupDao
import org.marino.tfgpagao.data.local.dao.MemberDao
import org.marino.tfgpagao.data.local.dao.ParticipationDao
import org.marino.tfgpagao.data.local.dao.ReceiptDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constants.APPDB
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGroupDao(appDatabase: AppDatabase): GroupDao {
        return appDatabase.groupDao()
    }

    @Provides
    fun provideMemberDao(appDatabase: AppDatabase): MemberDao {
        return appDatabase.memberDao()
    }

    @Provides
    fun provideReceiptDao(appDatabase: AppDatabase): ReceiptDao {
        return appDatabase.receiptDao()
    }

    @Provides
    fun provideParticipationDao(appDatabase: AppDatabase): ParticipationDao {
        return appDatabase.participationDao()
    }
}