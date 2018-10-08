package com.mooveit.genesis.di.service

import com.moczul.ok2curl.CurlInterceptor
import com.mooveit.genesis.model.build.BuildInfo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class OkHttpModule {
  companion object {
    const val OK_HTTP_KEY = "OkHttp"
    const val Ok_2_CURL_KEY = "Ok2Curl"
  }

  @Provides
  @Singleton
  fun provideServiceOkHttpClient(
      buildInfo: BuildInfo,
      loggingInterceptors: Set<@JvmSuppressWildcards Interceptor>
  ) = OkHttpClient.Builder().let {
    addLoggingInterceptor(it, buildInfo, loggingInterceptors)
    it.build()
  }

  @IntoSet
  @Provides
  @Singleton
  fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { Timber.tag(OK_HTTP_KEY).d(it) }
      .apply { level = HttpLoggingInterceptor.Level.BODY }

  @IntoSet
  @Provides
  @Singleton
  fun provideCurlInterceptor(): Interceptor = CurlInterceptor { Timber.tag(Ok_2_CURL_KEY).d(it) }

  private fun addLoggingInterceptor(
      clientBuilder: OkHttpClient.Builder, buildInfo: BuildInfo,
      loggingInterceptors: Set<Interceptor>
  ) {
    if (buildInfo.isDebug) {
      loggingInterceptors.forEach { clientBuilder.addNetworkInterceptor(it) }
    }
  }
}
