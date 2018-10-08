package com.mooveit.genesis.helper

inline fun <T, R> T.perform(block: T.() -> R) {
  block()
}

inline fun <T, R> T?.performIfPresent(block: T.() -> R) {
  this?.let(block)
}
