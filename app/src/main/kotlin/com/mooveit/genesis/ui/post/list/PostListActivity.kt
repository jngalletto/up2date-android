package com.mooveit.genesis.ui.post.list

import com.mooveit.genesis.router.annotation.Navigable
import com.mooveit.genesis.ui.core.activity.BaseActivity

@Navigable(viewModelClass = PostListViewModel::class)
class PostListActivity : BaseActivity() {
  override val fragment = PostListFragment()
}
