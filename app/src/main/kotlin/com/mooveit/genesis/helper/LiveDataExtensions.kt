package com.mapright.android.helper

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import kotlinx.coroutines.experimental.launch

fun <T, R> LiveData<T>.map(transformation: T.() -> R): LiveData<R> = Transformations.map(this, transformation)

fun <T> LiveData<T>.asyncPerform(function: suspend (T?) -> Unit) = asyncMap {
  it.also { element -> function(element) }
}

fun <T, R> LiveData<T>.asyncMap(function: suspend (T?) -> (R?)) = MediatorLiveData<R>().also {
  it.addSource(this) { element ->
    launch {
      val transformedElement = function(element)
      it.postValue(transformedElement)
    }
  }
}

fun <T> LiveData<T>.ignoreWhen(ignore: (T?, T?) -> Boolean) = MediatorLiveData<T>().also {
  var oldElement: T? = null

  it.addSource(this) { newElement ->
    if (oldElement == null || !ignore(oldElement, newElement)) {
      it.postValue(newElement)
    }
    oldElement = newElement
  }
}
