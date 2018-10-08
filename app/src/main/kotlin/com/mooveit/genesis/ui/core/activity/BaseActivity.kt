package com.mooveit.genesis.ui.core.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import com.mooveit.genesis.R
import com.mooveit.genesis.R.layout.activity_base
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
  protected open val fragment: Fragment? = null
  protected open val layoutResId: Int
    @LayoutRes
    get() = activity_base
  protected open val singleFragment: Fragment?
    get() = supportFragmentManager.findFragmentById(R.id.container)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutResId)

    if (singleFragment == null) {
      fragment?.run {
        arguments = intent.extras

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, this)
            .commit()
      }
    }
  }
}
