package com.mooveit.genesis.ui.core.fragment

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mooveit.genesis.NavigableBinder
import com.mooveit.genesis.Router
import com.mooveit.genesis.router
import com.mooveit.genesis.ui.core.viewmodel.BaseViewModel

abstract class BaseNavigableFragment<T> : BaseFragment() where T : BaseViewModel {
  protected abstract val viewModelClass: Class<T>
  protected lateinit var viewModel: T

  protected val router: Router?
    get() = activity?.router

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val view = super.onCreateView(inflater, container, savedInstanceState)

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    arguments?.let { NavigableBinder.bind(viewModel, it) }
    viewModel.setup()

    return view
  }

  protected fun Intent.navigate() = activity?.startActivity(this)

  protected fun Intent.navigateAndFinish() = activity?.let {
    it.startActivity(this)
    it.finish()
  }

  protected fun showAlert(
      @StringRes title: Int,
      @StringRes message: Int,
      @StringRes buttonText: Int = android.R.string.ok,
      action: ((DialogInterface) -> Unit)? = null
  ): Unit = AlertDialog.Builder(context)
      .setTitle(title)
      .setMessage(message)
      .setCancelable(true)
      .setNegativeButton(buttonText) { dialog, _ -> action?.invoke(dialog) ?: dialog.dismiss() }
      .create()
      .show()
}
