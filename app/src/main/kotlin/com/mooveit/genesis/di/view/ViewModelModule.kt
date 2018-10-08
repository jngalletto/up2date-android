package com.mooveit.genesis.di.view

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mooveit.genesis.ui.core.viewmodel.ViewModelFactory
import com.mooveit.genesis.ui.core.viewmodel.ViewModelKey
import com.mooveit.genesis.ui.post.detail.PostDetailViewModel
import com.mooveit.genesis.ui.post.list.PostListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(PostListViewModel::class)
  internal abstract fun bindPostListViewModel(viewModel: PostListViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(PostDetailViewModel::class)
  internal abstract fun bindPostDetailViewModel(viewModel: PostDetailViewModel): ViewModel
}
