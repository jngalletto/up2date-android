package com.mooveit.genesis.ui.core.viewmodel

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
  abstract fun setup()
}
