package com.mooveit.genesis.domain

import com.mooveit.genesis.provider.PostProvider
import javax.inject.Inject

class GetPostCommentsUseCase @Inject constructor(private val postRepository: PostProvider) {
  fun getPostComments(postId: Int) = postRepository.getPostComments(postId)
}
