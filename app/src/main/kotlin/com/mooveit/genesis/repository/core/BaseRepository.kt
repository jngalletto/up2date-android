package com.mooveit.genesis.repository.core

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch

abstract class BaseRepository {
  protected fun <T> fetchFromService(serviceCall: Deferred<T>): LiveData<Resource<T>> =
      MutableLiveData<Resource<T>>().also {
        it.postValue(Resource.loading())

        launch {
          try {
            val serviceData = serviceCall.await()
            it.postValue(Resource.success(serviceData))
          } catch (error: Exception) {
            it.postValue(Resource.error(error))
          }
        }
      }
}
