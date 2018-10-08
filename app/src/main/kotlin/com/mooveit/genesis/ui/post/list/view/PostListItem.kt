package com.mooveit.genesis.ui.post.list.view

import android.view.View
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.ui.common.SingleItemViewHolder
import kotlinx.android.synthetic.main.view_post_list_item.view.*

class PostListItem(val view: View, val onClick: (Post) -> Unit) : SingleItemViewHolder<Post>(view) {
  override fun bind(item: Post) {
    view.apply {
      setOnClickListener { onClick(item) }

      title.text = item.title
      body.text = item.body
    }
  }
}
