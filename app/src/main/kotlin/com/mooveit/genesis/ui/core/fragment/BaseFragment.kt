package com.mooveit.genesis.ui.core.fragment

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @get:LayoutRes
  protected abstract val layoutResId: Int

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
      inflater.inflate(layoutResId, container, false)
}
