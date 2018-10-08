package com.mooveit.genesis.di.repository

import com.mooveit.genesis.provider.PostProvider
import com.mooveit.genesis.repository.PostRepository
import com.mooveit.genesis.service.PostService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
  @Provides
  @Singleton
  fun providePostRepository(postService: PostService): PostProvider = PostRepository(postService)
}
