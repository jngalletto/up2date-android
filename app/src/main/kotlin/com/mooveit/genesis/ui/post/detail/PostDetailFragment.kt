package com.mooveit.genesis.ui.post.detail

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mapright.android.helper.gone
import com.mooveit.genesis.R
import com.mooveit.genesis.ui.core.fragment.BaseNavigableFragment
import kotlinx.android.synthetic.main.fragment_post_detail.*
import android.content.Intent
import android.net.Uri


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
            it.content?.let { postContent ->
                if (postContent.length > 258) {
                    val finalText = "${postContent.substring(0, 258)}... [continue reading]"
                    with(SpannableStringBuilder(finalText)) {
                        setSpan(ForegroundColorSpan(Color.rgb(255, 121, 0)), 262, 280, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                        setSpan(StyleSpan(Typeface.BOLD), 262, 280, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                        content.text = this
                    }
                } else {
                    content.text = postContent
                }
            }

            content.setOnClickListener { _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }

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
