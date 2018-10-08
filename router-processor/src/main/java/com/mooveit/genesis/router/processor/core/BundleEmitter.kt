package com.mooveit.genesis.router.processor.core

import com.mooveit.genesis.router.annotation.NavigableArg
import com.mooveit.genesis.router.processor.common.Constants
import com.mooveit.genesis.router.processor.common.Types
import com.mooveit.genesis.router.processor.common.getTypeFromDescription
import com.mooveit.genesis.router.processor.common.importFromType
import com.squareup.kotlinpoet.*
import org.jetbrains.annotations.Nullable
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

class BundleEmitter(private val `class`: TypeMirror, private val processingEnv: ProcessingEnvironment) {
  companion object {
    const val SUPPRESS_PARAMETER = "\"UNUSED_PARAMETER\""
    val PRIMITIVE_TYPES = listOf("byte", "char", "short", "float", "int", "boolean",
        "long", "double", "string", "charsequence", "size", "sizef")
    val COMPLEX_TYPES = mapOf(
        Types.PARCELABLE to Types.PARCELABLE.`class`,
        Types.BUNDLE to Types.BUNDLE.`class`,
        Types.SERIALIZABLE to Types.SERIALIZABLE.`class`,
        Types.I_BINDER to Types.I_BINDER.`class`
    )
  }

  private val bundleTypes = COMPLEX_TYPES.mapKeys { processingEnv.getTypeFromDescription(it.key) }
  private val fields = `class`
      .let { processingEnv.typeUtils.asElement(it).enclosedElements }
      .asSequence()
      .filter { it.kind == ElementKind.FIELD && it.getAnnotation(NavigableArg::class.java) != null }
      .toList()

  fun emitToBundle(funcBuilder: FunSpec.Builder) {
    if (fields.isEmpty()) {
      return
    }

    funcBuilder.addStatement("val bundle = Bundle()")
    fields.forEach {
      var typeName = it.asType().asTypeName()
      val parameter: ParameterSpec.Builder

      if (it.getAnnotation(Nullable::class.java) != null) {
        typeName = typeName.asNullable()
        parameter = ParameterSpec.builder(it.toString(), typeName).defaultValue("null")
      } else {
        parameter = ParameterSpec.builder(it.toString(), typeName)
      }

      funcBuilder.addParameter(parameter.build())
      funcBuilder.addStatement("bundle.put${getTypeName(it)}(\"$it\", $it)")
    }
    funcBuilder.addStatement("intent.putExtras(bundle)")
  }

  fun emitFromBundle() {
    val packageDirectory = processingEnv.options[Constants.KOTLIN_GENERATED_PACKAGE]
    val bundleClass = processingEnv.elementUtils.getTypeElement("android.os.Bundle")
    val className = processingEnv.typeUtils.asElement(`class`).simpleName

    val file = FileSpec.builder(Constants.GENESIS_PACKAGE, "${className}_Generated")
    file.importFromType(Types.BUNDLE)

    val funSpec = FunSpec.builder(Types.UNPACK_FROM_BUNDLE.`class`)
    funSpec.addAnnotation(AnnotationSpec.builder(Suppress::class).addMember(SUPPRESS_PARAMETER).build())
    funSpec.receiver(`class`.asTypeName())
    funSpec.addParameter("bundle", bundleClass.asType().asTypeName())

    fields.forEach {
      funSpec.addStatement("$it = bundle.get${getTypeName(it)}(\"$it\")")
    }

    file.addFunction(funSpec.build())
    file.build().writeTo(File(packageDirectory))
  }

  private fun getTypeName(element: Element): String {
    var type = bundleTypes
        .keys
        .asSequence()
        .filter { processingEnv.typeUtils.isSubtype(element.asType(), it.asType()) }
        .firstOrNull()
        ?.let { bundleTypes[it] }

    if (type == null && PRIMITIVE_TYPES.contains(element.asType().toString().toLowerCase())) {
      type = element.asType().toString().capitalize()
    }

    if (type == null || type.isBlank()) {
      val message = "The type ${element.asType()} cannot be put into a bundle."
      processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message, element)
    }

    return type ?: ""
  }
}
