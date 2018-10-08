package com.mooveit.genesis.router.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Navigable(val viewModelClass: KClass<out Any>)
