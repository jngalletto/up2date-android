package com.mooveit.genesis.repository

import com.mooveit.genesis.provider.PostProvider
import com.mooveit.genesis.repository.core.BaseRepository
import com.mooveit.genesis.service.PostService

class PostRepository(private val postService: PostService) : BaseRepository(), PostProvider {
  override fun getPosts() = fetchFromService(postService.getPosts())

  override fun getPostComments(id: Int) = fetchFromService(postService.getCommentsForPost(id))
}
