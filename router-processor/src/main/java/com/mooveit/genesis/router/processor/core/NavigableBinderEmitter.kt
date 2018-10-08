package com.mooveit.genesis.router.processor.core

import com.mooveit.genesis.router.processor.common.Constants
import com.mooveit.genesis.router.processor.common.Types
import com.mooveit.genesis.router.processor.common.getTypeFromDescription
import com.mooveit.genesis.router.processor.common.importFromType
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.type.TypeMirror

class NavigableBinderEmitter(private val processingEnv: ProcessingEnvironment) {
  companion object {
    const val NAVIGABLE_BINDER_CLASS = "NavigableBinder"
  }

  private val file: FileSpec.Builder
  private val navigableBinderClass: TypeSpec.Builder
  private val companionObject: TypeSpec.Builder
  private val function: FunSpec.Builder

  init {
    val bundleClass = processingEnv.getTypeFromDescription(Types.BUNDLE)

    file = FileSpec.builder(Constants.GENESIS_PACKAGE, NAVIGABLE_BINDER_CLASS)
    file.importFromType(Types.UNPACK_FROM_BUNDLE)
    file.importFromType(Types.BUNDLE)

    navigableBinderClass = TypeSpec.classBuilder(NAVIGABLE_BINDER_CLASS)
    companionObject = TypeSpec.companionObjectBuilder()

    function = FunSpec.builder("bind")
    function.addParameter("viewModel", ANY)
    function.addParameter("bundle", bundleClass.asType().asTypeName())
    function.beginControlFlow("when (viewModel)")
  }

  fun addViewModel(type: TypeMirror?) {
    type?.let {
      val className = processingEnv.typeUtils.asElement(it)
      function.addStatement("is ${className.asType().asTypeName()} -> viewModel.unpackFromBundle(bundle)")
    }
  }

  fun emit() {
    val packageDirectory = processingEnv.options[Constants.KOTLIN_GENERATED_PACKAGE]

    function.endControlFlow()
    companionObject.addFunction(function.build())
    navigableBinderClass.addType(companionObject.build())

    file.addType(navigableBinderClass.build())
    file.build().writeTo(File(packageDirectory))
  }
}
