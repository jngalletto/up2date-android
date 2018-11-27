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
        const val API_KEY = "apiKey"
        const val API_KEY_VALUE = "17c9920762bd4f5f94241bfdd0008d19"
    }

    @Provides
    @Singleton
    fun provideServiceOkHttpClient(
            buildInfo: BuildInfo,
            loggingInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ) = OkHttpClient.Builder().let {
        addLoggingInterceptor(it, buildInfo, loggingInterceptors)
        addApiKey(it)
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

    private fun addApiKey(clientBuilder: OkHttpClient.Builder) {
        clientBuilder.interceptors().add(Interceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().addQueryParameter(API_KEY, API_KEY_VALUE).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        })
    }
}
