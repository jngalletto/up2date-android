package com.mooveit.genesis.di.view

import com.mooveit.genesis.ui.core.fragment.BaseFragment
import com.mooveit.genesis.ui.post.detail.PostDetailFragment
import com.mooveit.genesis.ui.post.list.PostListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
  @ContributesAndroidInjector
  abstract fun bindBaseFragment(): BaseFragment

  @ContributesAndroidInjector
  abstract fun bindPostListFragment(): PostListFragment

  @ContributesAndroidInjector
  abstract fun bindPostDetailFragment(): PostDetailFragment
}
