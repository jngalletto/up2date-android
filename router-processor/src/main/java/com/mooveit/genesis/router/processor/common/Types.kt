package com.mooveit.genesis.router.processor.common

import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

enum class Types(val `package`: String, val `class`: String) {
  BUNDLE(Constants.OS_PACKAGE, "Bundle"),
  PARCELABLE(Constants.OS_PACKAGE, "Parcelable"),
  I_BINDER(Constants.OS_PACKAGE, "IBinder"),

  CONTEXT(Constants.CONTENT_PACKAGE, "Context"),
  INTENT(Constants.CONTENT_PACKAGE, "Intent"),

  SERIALIZABLE(Constants.IO_PACKAGE, "Serializable"),

  UNPACK_FROM_BUNDLE(Constants.GENESIS_PACKAGE, "unpackFromBundle")
}

fun FileSpec.Builder.importFromType(type: Types) = addImport(type.`package`, type.`class`)

fun ProcessingEnvironment.getTypeFromDescription(type: Types): TypeElement =
    elementUtils.getTypeElement("${type.`package`}.${type.`class`}")
