package com.mooveit.genesis.di.service

import com.mooveit.genesis.service.PostService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceApiModule {
  @Provides
  @Singleton
  fun providePostService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)
}
