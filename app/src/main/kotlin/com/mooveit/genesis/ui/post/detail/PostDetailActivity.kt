package com.mooveit.genesis.ui.post.detail

import com.mooveit.genesis.router.annotation.Navigable
import com.mooveit.genesis.ui.core.activity.BaseActivity

@Navigable(viewModelClass = PostDetailViewModel::class)
class PostDetailActivity : BaseActivity() {
  override val fragment = PostDetailFragment()
}
