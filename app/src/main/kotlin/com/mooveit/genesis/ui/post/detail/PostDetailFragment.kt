package com.mooveit.genesis.ui.post.detail

import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mapright.android.helper.gone
import com.mooveit.genesis.R
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
    viewModel.post?.let {
      title.text = it.title
      body.text = it.description
        content.text = it.content
      activity?.let { context ->
        it.urlToImage?.let {
          Glide.with(context)
              .asBitmap()
              .load(it)
              .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
              .into(newsImage)
        } ?: run {
          newsImage.gone()
        }
      }
    }
  }
}
