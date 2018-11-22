package com.mooveit.genesis.service

import com.mooveit.genesis.model.fetchPostsResponse.FetchPostsResponse
import com.mooveit.genesis.model.post.Post
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    companion object {
        const val API_KEY = "apiKey"
        const val API_KEY_VALUE = "17c9920762bd4f5f94241bfdd0008d19"
        const val COUNTRY_KEY = "country"
        const val DEFAULT_COUNTRY_CODE = "us"

        const val HEADLINES_URL = "top-headlines"
    }

    @GET(HEADLINES_URL)
    fun getTopHeadLines(
            @Query(COUNTRY_KEY) country: String = DEFAULT_COUNTRY_CODE,
            @Query(API_KEY) apiKey: String = API_KEY_VALUE
    ): Deferred<FetchPostsResponse>
}
