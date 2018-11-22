package com.mooveit.genesis.model.fetchPostsResponse

import android.os.Parcelable
import com.mooveit.genesis.model.post.Post
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FetchPostsResponse(
        val status: String = "",
        val totalResults: Int = 0,
        val articles: List<Post> = listOf<Post>()
) : Parcelable
