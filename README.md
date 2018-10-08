Genesis - Android Base Project
============================================

Genesis is a project template that provides a simple, scalable architecture to develop modern Android applications written in Kotlin

# Content

- [Dependencies](#dependencies)
- [Usage](#usage)
- [Architecture](#architecture)
    - [Model Layer](#model-layer)
    - [Domain Layer](#domain-layer)
    - [Presentation Layer](#presentation-layer)
        - [Models](#models)
        - [Views](#views)
        - [ViewModels](#viewmodels)
        - [Routing](#routing)
    - [Dependency Injection](#dependency-injection)
- [Style Guide](#style-guide)

# Dependencies

- `ViewModel & LiveData` from the Android Architecture Components used in the MVVM implementation
- `Ok2Curl` to convert OkHttp requests into curl logs
- `Dagger 2` as the dependency injection framework
- `ThreeTenAbp` as the Java 8 LocalDate backport
- `Timber` as the Logger
- `OkHttp Logging Interceptor` to log the HTTP request and response data
- `Retrofit` as the HTTP client
- `Kotlin Coroutines` to handle asynchronous operations

# Usage

1. Clone this repository
2. Change the git project remote url to one of your own with `git remote set-url origin https://github.com/USERNAME/REPOSITORY.git`.
3. Replace the applicationId & folder structure to match it
4. Remove the example code from all the packages
5. Remove the `fragment_` & `view_` layouts
6. You're all set

# Architecture

Genesis uses a lightweight Clean Architecture approach coupled with the Android Architecture Components following the Dependency Rule, that is, having all the dependencies pointing only inwards to an increasingly generic code.

## Model Layer

This layer contains all the Repositories for the domain layer to use. Each Repository should `provide` the manipulation of a single model. This manipulation is specified in a Provider contract, to decouple the model layer from the domain layer.

The Repository is then instanciated by Dagger as an instance of the Provider contract, ready to be injected anywhere in the domain layer where it's needed.

## Domain Layer

The domain layer contains all the use cases of the application. UseCases query the model layer for information and transforms it into something useful for the views.

UseCases should do one thing only and do it well. That will allow to compose multiple use cases to easily implement a complex operation maintaining everything in one place.

## Presentation Layer

This layer uses the MVVM architecture to fully leverage the Android Architecture Components, using the models wrapped in LiveData instances as the Single Source of Truth.

### Models

Since the business logic is encapsulated in the domain layer, the models act only as data stores and hence should be easily implemented with Kotlin data classes.

### Views

The Views are the Activities and Fragments of the application.

The Activity main job is to hold a Fragment instance. Since there're some actions that can only be done or controlled by the Activity, it's best to have one per group of functionallity.

The Fragment is then charged with the task of actually displaying the view, by listening and reacting to the events emitted by the ViewModel.

Both Activities and Fragments must be added to their respective Dagger modules.

### ViewModels

The ViewModel is the one that drives what happens in the View by emitting different events when needed.

For the events, the ViewModel will expose `LiveData` instances that the Views can observe for changes.

This `LiveData` objects will come from the domain layer, and, in turn, from the model layer. By doing this, we can really honor the Single Source of Truth principle and avoid unnecessary bugs due to unexpected mutations to the data.

ViewModels must also be added to the corresponding Dagger module.

### Routing

The normal flow of an application usually involves many activities. We call routing the action of moving between them.

The Genesis approach was designed with simplicity in mind, specially for:
- Passing of parameters between views
- Keeping the views agnostic of these parameters, as they should only be handled by ViewModels

To create a new route, you must do the following:

1 - extend the `Activity` from `BaseActivitty` and add the `@Navigable` annotation:
```kotlin
@Navigable(viewModelClass = PostListViewModel::class)
```
2 - extend the `Fragment` from `BaseNavigableFragment` with the desired `ViewModel` as the template parameter.
```kotlin
class PostListFragment : BaseNavigableFragment<PostListViewModel>()
```

3 - in the `ViewModel`, mark the parameters you want to receive with the `@NavigableArg` annotation:
```kotlin
@NavigableArg
internal var post: Post? = null
```

After building, a `Router` class will be generated with a route method for each `@Navigable` activity. From the `Fragment`, it can be called as such:

```kotlin
router?.routeToPostDetailActivity(it)?.navigate()
```

The routeTo method return an `Intent` instance to add extra configurations if needed.

## Dependency Injection

Genesis uses Dagger 2 with support for the Android Injector. Modules for Activities, Fragments, ViewModels, Retrofit and OkHttp are provided.

# Style Guide

Genesis follows the [Kotlin Coding Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html), but uses two spaces instead of four for indentation.
