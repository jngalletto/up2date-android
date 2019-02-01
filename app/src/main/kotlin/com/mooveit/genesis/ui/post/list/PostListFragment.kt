package com.mooveit.genesis.ui.post.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mooveit.genesis.R
import com.mooveit.genesis.R.id.appBarLayout
import com.mooveit.genesis.helper.observe
import com.mooveit.genesis.model.fetchPostsResponse.FetchPostsResponse
import com.mooveit.genesis.repository.core.Resource
import com.mooveit.genesis.ui.common.SingleItemAdapter
import com.mooveit.genesis.ui.core.fragment.BaseNavigableFragment
import com.mooveit.genesis.ui.post.list.view.PostListItem
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class PostListFragment : BaseNavigableFragment<PostListViewModel>() {
    override val layoutResId = R.layout.fragment_post_list
    override val viewModelClass = PostListViewModel::class.java

    private val postsAdapter by lazy {
        context?.let { SingleItemAdapter(it, R.layout.view_post_list_item, this::createItem) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compatActivity?.setSupportActionBar(appBarLayout.toolbar)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        setupView()
        viewModel.run {
            observe(posts, ::postsLoaded)
        }
    }

    private fun setupView() = recyclerView.apply {
        layoutManager = LinearLayoutManager(activity)
        adapter = postsAdapter
    }

    private fun postsLoaded(posts: Resource<FetchPostsResponse>?) {
        posts?.value?.let { postsAdapter?.items = it.articles }
    }

    private fun createItem(view: View) = PostListItem(view) {
        router?.routeToPostDetailActivity(it)?.navigate()
    }
}
