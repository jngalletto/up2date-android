package com.mooveit.genesis

import android.content.Context
import android.content.ContextWrapper
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mooveit.genesis.di.DaggerAppComponent
import com.pixplicity.easyprefs.library.Prefs
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this).apply {
            inject(this@App)
        }

    operator fun get(context: Context): App {
        return context.applicationContext as App
    }

    override fun onCreate() {
        super.onCreate()

        setupLocalDateBackport()
        setupLogging()
        initPreferences()
    }

    private fun initPreferences() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    private fun setupLocalDateBackport() = AndroidThreeTen.init(this)

    private fun setupLogging() = Timber.plant(Timber.DebugTree())
}
