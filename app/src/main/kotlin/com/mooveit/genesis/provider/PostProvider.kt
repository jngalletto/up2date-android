package com.mooveit.genesis.provider

import android.arch.lifecycle.LiveData
import com.mooveit.genesis.model.fetchPostsResponse.FetchPostsResponse
import com.mooveit.genesis.model.post.Post
import com.mooveit.genesis.repository.core.Resource

interface PostProvider {
  fun getTopHeadlines(countryCode: String = ""): LiveData<Resource<FetchPostsResponse>>
}
