package com.mooveit.genesis.domain

import com.mooveit.genesis.provider.PostProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostsUseCase @Inject constructor(private val postRepository: PostProvider) {
  fun getPosts() = postRepository.getTopHeadlines()
}
