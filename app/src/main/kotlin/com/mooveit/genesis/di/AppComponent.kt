package com.mooveit.genesis.di

import com.mooveit.genesis.App
import com.mooveit.genesis.di.repository.RepositoryModule
import com.mooveit.genesis.di.service.OkHttpModule
import com.mooveit.genesis.di.service.ServiceApiModule
import com.mooveit.genesis.di.service.ServiceModule
import com.mooveit.genesis.di.view.ActivityModule
import com.mooveit.genesis.di.view.AppModule
import com.mooveit.genesis.di.view.FragmentModule
import com.mooveit.genesis.di.view.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      AndroidSupportInjectionModule::class,
      AppModule::class,
      ActivityModule::class,
      FragmentModule::class,
      ViewModelModule::class,
      RepositoryModule::class,
      ServiceApiModule::class,
      ServiceModule::class,
      OkHttpModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()
}
