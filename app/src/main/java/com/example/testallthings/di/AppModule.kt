package com.example.testallthings.di

import android.content.Context
import com.example.testallthings.common.Constants.BASE_URL
import com.example.testallthings.network.Api
import com.example.testallthings.utils.prefUtils.PrefKeys.USER_TOKEN
import com.example.testallthings.utils.prefUtils.PrefUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            it.proceed(
                (it.request().newBuilder().addHeader(
                    "Authorization",
                    "Bearer ${PrefUtils.getFromPref(context, USER_TOKEN, "")}"
                ).build())
            )
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }


}