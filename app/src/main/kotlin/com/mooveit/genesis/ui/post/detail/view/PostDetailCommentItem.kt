package com.mooveit.genesis.ui.post.detail.view

import android.view.View
import com.mooveit.genesis.model.comment.Comment
import com.mooveit.genesis.ui.common.SingleItemViewHolder
import kotlinx.android.synthetic.main.view_post_detail_comment_item.view.*

class PostDetailCommentItem(val view: View) : SingleItemViewHolder<Comment>(view) {
  override fun bind(item: Comment) {
    view.body.text = item.body
  }
}
