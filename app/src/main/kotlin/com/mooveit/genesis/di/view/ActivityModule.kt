package com.mooveit.genesis.di.view

import com.mooveit.genesis.ui.core.activity.BaseActivity
import com.mooveit.genesis.ui.post.detail.PostDetailActivity
import com.mooveit.genesis.ui.post.list.PostListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
  @ContributesAndroidInjector(modules = [FragmentModule::class])
  abstract fun bindBaseActivity(): BaseActivity

  @ContributesAndroidInjector(modules = [FragmentModule::class])
  abstract fun bindPostDetailActivity(): PostDetailActivity

  @ContributesAndroidInjector(modules = [FragmentModule::class])
  abstract fun bindPostListActivity(): PostListActivity
}
