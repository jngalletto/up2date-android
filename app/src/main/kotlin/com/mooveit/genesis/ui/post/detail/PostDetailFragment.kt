package com.mooveit.genesis.ui.post.detail

import android.support.v7.widget.LinearLayoutManager
import com.mooveit.genesis.R
import com.mooveit.genesis.R.id.*
import com.mooveit.genesis.helper.observe
import com.mooveit.genesis.helper.performIfPresent
import com.mooveit.genesis.repository.core.Resource
import com.mooveit.genesis.repository.core.ResourceStatus
import com.mooveit.genesis.ui.common.SingleItemAdapter
import com.mooveit.genesis.ui.core.fragment.BaseNavigableFragment
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment : BaseNavigableFragment<PostDetailViewModel>() {
  override val layoutResId = R.layout.fragment_post_detail
  override val viewModelClass = PostDetailViewModel::class.java

  override fun onStart() {
    super.onStart()

    setupView()
  }

  private fun setupView() {
    commentsRecyclerView.apply {
      layoutManager = LinearLayoutManager(activity)
    }

    viewModel.post?.let {
      title.text = it.title
      body.text = it.description
    }
  }
}
