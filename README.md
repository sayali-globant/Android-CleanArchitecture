# Android-MVVM-CleanArchitecture

The structure of this project is bit different -- data and domain package are outside the app for
more segregation (based on multi module concept)

### Android Demo fo famous Marvel characters.

- app module represents Presentation layer (Activities, Fragments, ViewModels etc.)
- data module represents Data layer (Repositories, Data source etc.)
- domain module represents Domain layer (Use cases, Entities etc.)

- APIs used from _[Marvel API](https://developer.marvel.com/docs)_ docs. You need to create an
  account in order to generate Public Key & Private Key to use its APIs.
  **Also check the authorisation part very carefully**

## Tech stack & Open-source libraries

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based
  + [Couroutines](https://github.com/Kotlin/kotlinx.coroutines)- for asynchronous
- Jetpack -- Lifecycle - dispose of observing data when lifecycle state changes. -- ViewModel - UI
  related data holder, lifecycle aware.
- Architecture - MVVM
- [Hilt](https://dagger.dev/hilt/) - Dependency injection.
- [Retrofit & Jackson](https://github.com/square/retrofit) - To construct REST APIs.
- [OkHttp3](https://github.com/square/okhttp) - To implement interceptor, logging etc.
- [Glide](https://github.com/bumptech/glide) - For image rendering.
- [Mockito](https://developer.android.com/training/testing/unit-testing/local-unit-tests): For Unit
  Testing.
