package com.mooveit.genesis.ui.post.detail

import android.support.v7.widget.LinearLayoutManager
import com.mooveit.genesis.R
import com.mooveit.genesis.helper.observe
import com.mooveit.genesis.helper.performIfPresent
import com.mooveit.genesis.model.comment.Comment
import com.mooveit.genesis.repository.core.Resource
import com.mooveit.genesis.repository.core.ResourceStatus
import com.mooveit.genesis.ui.common.SingleItemAdapter
import com.mooveit.genesis.ui.core.fragment.BaseNavigableFragment
import com.mooveit.genesis.ui.post.detail.view.PostDetailCommentItem
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment : BaseNavigableFragment<PostDetailViewModel>() {
  override val layoutResId = R.layout.fragment_post_detail
  override val viewModelClass = PostDetailViewModel::class.java

  private val commentsAdapter by lazy {
    activity?.let { SingleItemAdapter(it, R.layout.view_post_detail_comment_item, ::PostDetailCommentItem) }
  }

  override fun onStart() {
    super.onStart()

    setupView()
    viewModel.run {
      observe(comments, ::commentsLoaded)
    }
  }

  private fun setupView() {
    commentsRecyclerView.apply {
      layoutManager = LinearLayoutManager(activity)
      adapter = commentsAdapter
    }

    viewModel.post?.let {
      title.text = it.title
      body.text = it.body
    }
  }

  private fun commentsLoaded(comments: Resource<List<Comment>>?) = comments.performIfPresent {
    when (status) {
      ResourceStatus.LOADING -> commentsTitle.text = getString(R.string.comments_with_amount, 0)
      else -> value.performIfPresent {
        commentsTitle.text = getString(R.string.comments_with_amount, size)
        commentsAdapter?.items = this
      }
    }
  }
}