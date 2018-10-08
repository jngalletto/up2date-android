package com.mooveit.genesis

import com.jakewharton.threetenabp.AndroidThreeTen
import com.mooveit.genesis.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
      DaggerAppComponent.builder().create(this).apply {
        inject(this@App)
      }

  override fun onCreate() {
    super.onCreate()

    setupLocalDateBackport()
    setupLogging()
  }

  private fun setupLocalDateBackport() = AndroidThreeTen.init(this)

  private fun setupLogging() = Timber.plant(Timber.DebugTree())
}
