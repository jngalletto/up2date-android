package com.mapright.android.helper

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.cast() =
    if (all { it is T }) this as List<T> else null
