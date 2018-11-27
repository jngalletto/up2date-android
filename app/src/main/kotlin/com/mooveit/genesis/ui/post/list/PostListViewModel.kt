package com.mooveit.genesis.ui.post.list

import android.arch.lifecycle.LiveData
import com.mooveit.genesis.domain.GetPostsUseCase
import com.mooveit.genesis.model.fetchPostsResponse.FetchPostsResponse
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.repository.core.Resource
import com.mooveit.genesis.ui.core.viewmodel.BaseViewModel
import javax.inject.Inject

class PostListViewModel @Inject constructor(private val getPostsUseCase: GetPostsUseCase) : BaseViewModel() {
  lateinit var posts: LiveData<Resource<FetchPostsResponse>>

  override fun setup() {
    posts = getPostsUseCase.getPosts()
  }
}
