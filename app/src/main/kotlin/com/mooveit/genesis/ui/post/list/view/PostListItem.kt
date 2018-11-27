package com.mooveit.genesis.ui.post.list.view

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mapright.android.helper.visibleIf
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.ui.common.SingleItemViewHolder
import kotlinx.android.synthetic.main.view_post_list_item.view.*

class PostListItem(val view: View, val onClick: (Post) -> Unit) : SingleItemViewHolder<Post>(view) {
    override fun bind(item: Post) {
        view.apply {
            setOnClickListener { onClick(item) }

            title.text = item.title
            body.text = item.description
            body.visibleIf { item.description != null }
            Glide.with(view)
                .load(item.urlToImage ?: "https://static01.nyt.com/images/2018/11/27/us/27dc-manafort1/27dc-manafort1-superJumbo.jpg?quality=90&auto=webp")
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(newsImage)
        }
    }
}
