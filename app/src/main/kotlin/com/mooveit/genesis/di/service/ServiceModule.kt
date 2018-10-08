package com.mooveit.genesis.di.service

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.mooveit.genesis.R
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {
  companion object {
    const val SERVICE_URL_NAME = "ServiceUrlName"
  }

  @Provides
  @Singleton
  fun provideRetrofit(
      client: OkHttpClient,
      callAdapterFactory: CoroutineCallAdapterFactory,
      gsonConverterFactory: GsonConverterFactory,
      @Named(SERVICE_URL_NAME) baseUrl: String
  ) = Retrofit.Builder()
      .addCallAdapterFactory(callAdapterFactory)
      .addConverterFactory(gsonConverterFactory)
      .baseUrl(baseUrl)
      .client(client)
      .build()

  @Provides
  @Singleton
  fun provideCoroutinesCallAdapterFactory() = CoroutineCallAdapterFactory()

  @Provides
  @Singleton
  fun provideGsonConverterFactory() = GsonConverterFactory.create()

  @Provides
  @Singleton
  @Named(SERVICE_URL_NAME)
  fun provideBaseUrl(context: Context) = context.getString(R.string.base_url)
}
