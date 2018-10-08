package com.mooveit.genesis.ui.post.detail

import android.arch.lifecycle.LiveData
import com.mooveit.genesis.domain.GetPostCommentsUseCase
import com.mooveit.genesis.model.comment.Comment
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.repository.core.Resource
import com.mooveit.genesis.router.annotation.NavigableArg
import com.mooveit.genesis.ui.core.viewmodel.BaseViewModel
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val getPostCommentsUseCase: GetPostCommentsUseCase
) : BaseViewModel() {
  @NavigableArg
  internal var post: Post? = null

  lateinit var comments: LiveData<Resource<List<Comment>>>

  override fun setup() {
    comments = getPostCommentsUseCase.getPostComments(post?.id ?: 0)
  }
}
