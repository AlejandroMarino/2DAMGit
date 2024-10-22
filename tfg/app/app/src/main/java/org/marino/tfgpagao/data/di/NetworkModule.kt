package org.marino.tfgpagao.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.marino.tfgpagao.common.Constants
import org.marino.tfgpagao.network.services.GroupService
import org.marino.tfgpagao.network.services.MemberService
import org.marino.tfgpagao.network.services.ParticipationService
import org.marino.tfgpagao.network.services.ReceiptService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideGroupService(retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }

    @Provides
    fun provideMemberService(retrofit: Retrofit): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Provides
    fun provideReceiptService(retrofit: Retrofit): ReceiptService {
        return retrofit.create(ReceiptService::class.java)
    }

    @Provides
    fun provideParticipationService(retrofit: Retrofit): ParticipationService {
        return retrofit.create(ParticipationService::class.java)
    }
}