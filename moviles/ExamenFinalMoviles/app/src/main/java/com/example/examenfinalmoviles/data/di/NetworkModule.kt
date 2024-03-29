package com.example.examenfinalmoviles.data.di

import com.example.examenfinalmoviles.common.Constantes
import com.example.examenfinalmoviles.data.remote.LocalDateConverter
import com.example.examenfinalmoviles.network.services.DiputadosService
import com.example.examenfinalmoviles.network.services.PartidosService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

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
        val gson =
            GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateConverter()).create()
        return Retrofit.Builder().baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideHospitalService(retrofit: Retrofit): PartidosService {
        return retrofit.create(PartidosService::class.java)
    }

    @Provides
    fun providePacienteService(retrofit: Retrofit): DiputadosService {
        return retrofit.create(DiputadosService::class.java)
    }
}