package com.mooveit.genesis.router.processor.core

import com.google.auto.service.AutoService
import com.mooveit.genesis.router.annotation.Navigable
import com.mooveit.genesis.router.processor.common.Constants
import com.mooveit.genesis.router.processor.common.Types
import com.mooveit.genesis.router.processor.common.getTypeFromDescription
import com.mooveit.genesis.router.processor.common.importFromType
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

@AutoService(Processor::class)
class RouterProcessor : AbstractProcessor() {
  companion object {
    const val ROUTER_CLASS = "Router"
    const val VIEW_MODEL_ANNOTATION_PARAM = "viewModelClass"
  }

  override fun getSupportedAnnotationTypes() = setOf(Navigable::class.java.canonicalName)

  override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

  override fun process(type: MutableSet<out TypeElement>?, env: RoundEnvironment?): Boolean {
    val navigableBinderEmitter = NavigableBinderEmitter(processingEnv)
    val navigableClasses = env?.getElementsAnnotatedWith(Navigable::class.java)
    val contextClass = processingEnv.getTypeFromDescription(Types.CONTEXT)

    val file = FileSpec.builder(Constants.GENESIS_PACKAGE, ROUTER_CLASS)
    val routerClass = TypeSpec.classBuilder(ROUTER_CLASS)

    val constructor = FunSpec.constructorBuilder()
        .addParameter("context", contextClass.asType().asTypeName())
        .build()
    routerClass.primaryConstructor(constructor)
        .addProperty(PropertySpec.builder("context", contextClass.asType().asTypeName())
            .initializer("context")
            .addModifiers(KModifier.PRIVATE)
            .build())

    listOf(Types.CONTEXT, Types.INTENT, Types.BUNDLE).forEach { file.importFromType(it) }

    navigableClasses?.forEach { element ->
      val viewPackage = processingEnv.elementUtils.getPackageOf(element).toString()
      file.addImport(viewPackage, element.simpleName.toString())

      val viewModelClass: TypeMirror? = getAnnotationValue(element, VIEW_MODEL_ANNOTATION_PARAM)
      val bundleEmitter = viewModelClass?.let { BundleEmitter(it, processingEnv) }

      val routeFunction = FunSpec.builder("routeTo${element.simpleName}")
      routeFunction.returns(processingEnv.getTypeFromDescription(Types.INTENT).asClassName())
      routeFunction.addStatement("val intent = Intent(context, ${element.simpleName}::class.java)")

      navigableBinderEmitter.addViewModel(viewModelClass)
      bundleEmitter?.emitToBundle(routeFunction)
      bundleEmitter?.emitFromBundle()

      routeFunction.addStatement("return intent")
      routerClass.addFunction(routeFunction.build())
    }

    if (navigableClasses?.isNotEmpty() == true) {
      navigableBinderEmitter.emit()

      val packageDirectory = processingEnv.options[Constants.KOTLIN_GENERATED_PACKAGE]
      val router = routerClass.build()
      val routerContextProperty = PropertySpec
          .builder("router", ClassName(Constants.GENESIS_PACKAGE, ROUTER_CLASS))
          .receiver(contextClass.asType().asTypeName())
          .getter(FunSpec.getterBuilder().addCode("return Router(this)\n").build())
          .build()

      file.addProperty(routerContextProperty)
      file.addType(router)
      file.build().writeTo(File(packageDirectory))
    }

    return true
  }

  private inline fun <reified T : Any> getAnnotationValue(element: Element, key: String) = element.annotationMirrors
      .map { it.elementValues }
      .map { values ->
        values.entries
            .filter { it.key.simpleName.toString() == key }
            .map { it.value.value as T }
      }
      .flatten()
      .firstOrNull()
}
