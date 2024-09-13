<img src="docs/images/news_app.svg" height="70"><br/>

# NewsApp Android Application

https://github.com/user-attachments/assets/ce1425f8-4cfa-4b74-b12f-c37712ab39e2

## Summary
News app is written in Kotlin, with the Android SDK and the Jetpack Compose UI Framework.

## Project setup & requirements
This application uses mostly standard processes and libraries.

To be able to develop the app you need to have
-  the latest Android Studio installed, the latest Kotlin (2.0.0), and the latest Android Gradle Plugin 8.4.0 (gradle-8.6-bin.zip) versions.
- general git setup,
- cloned the repository of News App application,

#### API KEY

My API key has been included in gragle.properties. As it's an assessment project I have decided to leave it there so reviewing team members can focus on running the app and reviewing the code. 

But since it is a free API, there is a rate limiter. only a specific amount of requests can be allowed in a given time frame.

in case this happens, you can create your own api key by signing up to https://newsdata.io/, it looks something like this `pub_5324732474707aa48524d6f83145cf364gf442`
copy the api key.. and paste it in gradle.properties. like so `NEWS_API_KEY="pYOUR_COPIED_API_KEY"`

Free Plan has 30 credits every 15 minutes.

If you exceed the rate limit for your plan, the API will return a "Rate Limit Exceeded" error and you will not be able to make any further requests until the rate limit resets after 15 minutes.

more documentation here
Docs(https://newsdata.io/documentation/#rate-limit)


##  Dependencies
The app uses pre-compiled gradle scripts to share dependencies between different modules.
The app has a multi-module structure, with 2 major groups: core and feature modules. To make the builds faster, scripts are pre-compiled once and reused in the respective modules.
Te app uses Gradle's version catalogs, to have a single source of truth where we define all dependencies used in the app.

#### Module description
- `:base` contains base classes for ViewModel and all connected logic, and can be needed for other core modules.
- `:common` it contains common shared implementation to be used at different modules but are not necessarily base [following OOP]. 
- `:network` module holds common network-related logic and it provides Retrofit client instance for other modules
- `:securestore` is an abstraction module for shared preference, and it is responsible for storing data.
- `:testing` contains common testing code, such as the BaseTest that is used for all ViewModel tests.
- `:ui` holds all the common ui elements, layouts, design guidelines such as colors, dimensions etc.
- `:di` provides hilt modules
- `localdata` contains implementation for local database set up, room, dao and type converters
- `models`contain the core domain entity whose visibility is needed across the app. API data is mapped to models here, and UI model is mapped form models here. its the base for our domain driven design and gives our app safety, stability and flexibility.
- `feature` contain submodules of each feature. each standalone module is further organized by presentation for UI, domain for interfaces and core operations [and data for cases where each needs its own data layer set up]
- `:app` module which can be compiled to an sdk contains the MainActivity, Application class and uses the feature modules to provide navigation and the entry point of the whole app. 

For the application to run, App Module depends on Feature Modules and Feature Modules depends on Core Module [some or all of its sub modules as the implementation requires].

### Dependency Injection
This project uses Hilt for Dependency Injection as a class should be responsible for creating their own dependencies, rather inject them possibly through their constructors.

## CI/CD 
This project uses Github Actions for CI which runs on every push or PR to main and develop.

#### Exception Handling
The app is designed that operations, like fetching data from the network or reading from local storage, are carried out using kotlinx.flows. 
This means that all internal business logic, data manipulation, and mapping occur within these flows.
If an exception occurs at any point, it gets passed up to the flow call site where the flow is being collected, typically within the view model. 
In the view model, there's a handleError function that handles the caught exception and determines how to display the corresponding error message or information on the UI.


## The following 3rd party libraries are used in the project.
```
coil
coil-svg
coil-compose
```
Coil is a powerful and efficient image loading library for Android. It is designed to simplify the process of loading and displaying

* Coil SVG is an extension of the Coil image loading library specifically designed for handling Scalable Vector Graphics (SVG) images in Android apps.

* Coil Compose is an extension of the Coil image loading library tailored for use with Jetpack Compose, Google's modern UI toolkit for Android.

Repo(https://github.com/coil-kt/coil)

```
coroutines-android
coroutines-core
coroutines-test
```
Coroutines Android is a part of the Kotlin Coroutines library and provides support for using Kotlin coroutines on Android platforms.

* Coroutines are a powerful tool for writing asynchronous, non-blocking code, and this library helps Android developers leverage them to simplify concurrency and asynchronous operations in android apps.

* Coroutines Test is a library that provides testing utilities for Kotlin coroutines.

Docs(https://kotlinlang.org/docs/coroutines-overview.html)

```
junit
junit-engine
```
JUnit Jupiter is a testing framework for Java and Kotlin offering a robust and flexible environment for writing and executing unit tests.

Docs(https://junit.org/junit5/docs/current/user-guide/)

```
hilt-android
hilt-android-compiler
hilt-navigation-compose
```
* Hilt Android is a dependency injection framework developed by Google for Android applications. It simplifies the process of managing dependencies and promotes best practices in dependency injection. With Hilt Android, developers can efficiently handle object creation and injection, making their code more modular and testable.

* Hilt Android Compiler is a compilation support tool that works in conjunction with Hilt Android. It processes Hilt-specific annotations during the build process, generating code to facilitate dependency injection

* Hilt Navigation Compose is an extension library for Hilt that enables seamless integration with Jetpack Compose and Android's navigation framework.

Docs(https://dagger.dev/hilt/)

```
kotlin-assertions
```
Kotest Assertions Core is a library that enhances the capabilities of Kotest, a testing framework for Kotlin. It provides a set of assertion functions and utilities to facilitate writing expressive and comprehensive test cases in Kotlin.

Docs(https://kotest.io/)
Docs(https://kotest.io/docs/quickstart)

```
mockK
```

MockK is a mocking library designed specifically for Kotlin. It simplifies the process of creating and working with mock objects in Kotlin-based unit tests.

Repo(https://github.com/mockk/mockk)

```
retrofit
retrofit-gson
```

* Retrofit is a widely-used library for making HTTP requests and communicating with web services in Android applications. It simplifies network calls, request and response handling, and integrates seamlessly with Android's networking libraries.

* Gson is a JSON parsing library for Android applications. It provides efficient and flexible JSON serialization and deserialization, making it an essential tool for working with JSON data in Android apps.

Repo(https://github.com/square/retrofit)
Docs(https://square.github.io/retrofit/)

```
logging-interceptor
```
Logging Interceptor is a component of OkHttp3, a popular HTTP client library for Android. This interceptor is used to log HTTP requests and responses, making it easier to debug and monitor network interactions within the app

Repo(https://github.com/square/okhttp/tree/master)

```
turbine
```
Turbine is a testing library for Kotlin Flow, a reactive stream processing library. Turbine simplifies the process of writing unit tests for code that uses Kotlin Flow, allowing developers to test asynchronous and reactive code in a structured and predictable manner.

Repo(https://github.com/cashapp/turbine)
```


```
room-ktx
room-runtime
room-compiler
kapt-room-compiler
```
EasyLog is a lightweight, simple, and flexible logging utility for Android applications. It provides a concise syntax for logging messages directly from objects and supports various default and custom loggers.
Repo(https://github.com/mikeisesele/easylog)
```

```
easylog
```
EasyLog is a lightweight, simple, and flexible logging utility for Android applications. It provides a concise syntax for logging messages directly from objects and supports various default and custom loggers.
Repo(https://github.com/mikeisesele/easylog)
```
