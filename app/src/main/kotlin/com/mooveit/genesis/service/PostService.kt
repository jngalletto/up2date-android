package com.mooveit.genesis.service

import com.mooveit.genesis.model.comment.Comment
import com.mooveit.genesis.model.post.Post
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
  companion object {
    const val COMMENT_PATH = "id"

    const val POST_URL = "posts"
    const val COMMENT_URL = "$POST_URL/{$COMMENT_PATH}/comments"
  }

  @GET(POST_URL)
  fun getPosts(): Deferred<List<Post>>

  @GET(COMMENT_URL)
  fun getCommentsForPost(@Path(COMMENT_PATH) id: Int): Deferred<List<Comment>>
}
