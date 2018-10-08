package com.mooveit.genesis.repository.core

data class Resource<T>(
    var status: ResourceStatus,
    var value: T? = null,
    val error: Exception? = null
) {
  companion object {
    fun <T> success(value: T) = Resource(ResourceStatus.SUCCESS, value)

    fun <T> error(error: Exception) = Resource<T>(ResourceStatus.ERROR, error = error)

    fun <T> loading() = Resource<T>(ResourceStatus.LOADING)
  }
}
