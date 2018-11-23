package com.mooveit.genesis.ui.post.detail

import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.router.annotation.NavigableArg
import com.mooveit.genesis.ui.core.viewmodel.BaseViewModel
import javax.inject.Inject

class PostDetailViewModel @Inject constructor() : BaseViewModel() {
  @NavigableArg
  internal var post: Post? = null

  override fun setup() {

  }
}
