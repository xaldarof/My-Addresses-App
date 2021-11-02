

object Dependencies {

    object Ui {
        object Versions {
            const val gmsPlayServices = "18.0.0"
            const val coordinatorLayout = "1.1.0"
            const val overScrollHelper = "1.1.1"
            const val fragmentKtx = "1.3.6"
            const val navigationFragmentKtx = "2.3.5"
            const val navigationUiKtx = "2.3.5"
            const val gmsPlayServicesMaps = "17.0.1"
        }

        const val gmsPlayServices = "com.google.android.gms:play-services-location:${Versions.gmsPlayServices}"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"
        const val overScrollHelper = "io.github.everythingme:overscroll-decor-android:${Versions.overScrollHelper}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"
        const val gmsPlayServicesMaps = "com.google.android.gms:play-services-maps:${Versions.gmsPlayServicesMaps}"
    }

    object Network {

        object Versions{
            const val retrofit = "2.9.0"
            const val retrofitConverter = "2.9.0"
            const val picasso = "2.71828"
            const val okHttpClientInterceptor = "4.9.1"
        }

       const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
       const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverter}"
       const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
       const val okHttpClientInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpClientInterceptor}"

    }


    object Cache {
        object Versions{
            const val room = "1.1.1"
            const val liveData = "2.3.1"
            const val roomRuntime = "2.3.0"
            const val roomKtx = ""
            const val roomCompiler = "2.3.0"
        }

        const val room = "android.arch.persistence.room:compiler:${Versions.room}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    }


    object Coroutines {
        object Versions {
            const val coroutines = "1.5.0"
        }

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Ktx {

        object Versions {
            const val viewModelKtx = "2.3.1"
            const val lifeCycleExtension = "2.2.0"
            const val coreKtx = "1.6.0"
        }

        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
        const val lifeCycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtension}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    }


    object Di {
        object Versions {
           const val legacySupport = "1.0.0"
           const val hiltCompiler = "1.0.0"
           const val hiltAndroidComponent = "2.30.1-alpha"
           const val hiltAndroidCompiler = "2.35.1"
           const val daggerHiltAndroid = "2.35"
           const val daggerHiltCompiler = "2.35"
           const val daggerHiltViewModel = "1.0.0-alpha03"
        }

        const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
        const val hiltCompiler =  "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
        const val hiltAndroidComponent = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidComponent}"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}"
        const val daggerHiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHiltAndroid}"
        const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.daggerHiltCompiler}"
        const val daggerHiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHiltViewModel}"
    }
}