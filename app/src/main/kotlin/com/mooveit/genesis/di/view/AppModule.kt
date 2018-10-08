package com.mooveit.genesis.di.view

import android.content.Context
import com.mooveit.genesis.App
import com.mooveit.genesis.model.build.BuildInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
  @Provides
  fun provideContext(app: App): Context = app.applicationContext

  @Provides
  @Singleton
  fun provideBuildInformation() = BuildInfo()
}
