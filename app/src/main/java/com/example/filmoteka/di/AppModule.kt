package com.example.filmoteka.di

import com.example.filmoteka.model.network.KinopoiskApiInterface
import com.example.filmoteka.repo.KinopoiskRepo
import com.example.filmoteka.repo.KinopoiskRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.kinopoisk.dev/"

    @Singleton
    @Provides
    fun provideRetrofit(base_url: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url).build()
    }

    @Provides
    fun provideKinopoiskApiService(retrofit: Retrofit): KinopoiskApiInterface {
        return retrofit.create(KinopoiskApiInterface::class.java)
    }

    @Provides
    fun provideKinopoiskRepository(api: KinopoiskApiInterface): KinopoiskRepo {
        return KinopoiskRepoImpl(api)
    }
}