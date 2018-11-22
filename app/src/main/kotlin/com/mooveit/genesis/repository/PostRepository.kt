package com.mooveit.genesis.repository

import com.mooveit.genesis.provider.PostProvider
import com.mooveit.genesis.repository.core.BaseRepository
import com.mooveit.genesis.service.PostService

class PostRepository(private val postService: PostService) : BaseRepository(), PostProvider {
    override fun getTopHeadlines(countryCode: String) = fetchFromService(postService.getTopHeadLines())
}
