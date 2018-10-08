package com.mooveit.genesis.provider

import android.arch.lifecycle.LiveData
import com.mooveit.genesis.model.comment.Comment
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.repository.core.Resource

interface PostProvider {
  fun getPosts(): LiveData<Resource<List<Post>>>
  fun getPostComments(id: Int): LiveData<Resource<List<Comment>>>
}
