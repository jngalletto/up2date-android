package com.mooveit.genesis.service

import com.mooveit.genesis.model.fetchPostsResponse.FetchPostsResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    companion object {
        const val COUNTRY_KEY = "country"
        const val DEFAULT_COUNTRY_CODE = "us"

        const val HEADLINES_URL = "top-headlines"
    }

    @GET(HEADLINES_URL)
    fun getTopHeadLines(
        @Query(COUNTRY_KEY) country: String = DEFAULT_COUNTRY_CODE
    ): Deferred<FetchPostsResponse>
}
