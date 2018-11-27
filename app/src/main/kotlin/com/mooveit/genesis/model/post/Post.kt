package com.mooveit.genesis.model.post

import android.os.Parcelable
import com.mooveit.genesis.model.source.Source
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val source: Source = Source(),
    val author: String? = "",
    val title: String = "",
    val description: String? = "",
    val url: String = "",
    val urlToImage: String? = "",
    val publishedAt: String = "",
    val content: String? = ""
) : Parcelable
