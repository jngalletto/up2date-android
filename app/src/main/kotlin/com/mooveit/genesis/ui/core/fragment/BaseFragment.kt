package com.mooveit.genesis.ui.core.fragment

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mooveit.genesis.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @get:LayoutRes
  protected abstract val layoutResId: Int

  protected val compatActivity
    get() = activity as? AppCompatActivity

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
      inflater.inflate(layoutResId, container, false)

  protected fun showSnackBar(
      message: String,
      @ColorRes backgroundColor: Int = R.color.colorPrimary
  ) = view?.let {
    Snackbar.make(it, message, Toast.LENGTH_SHORT).apply {
      view.setBackgroundColor(ContextCompat.getColor(it.context, backgroundColor))
      show()
    }
  }
}
