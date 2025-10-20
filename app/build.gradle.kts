plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-parcelize")
}

android {
    namespace   = "com.example.movielist"
    compileSdk  = 36

    defaultConfig {
        applicationId   = "com.example.movielist"
        minSdk          = 21
        targetSdk       = 36
        versionCode     = 1
        versionName     = "1.0"

        // TMDB API keys injected from gradle.properties
        buildConfigField("String", "TMDB_API_KEY" , "\"${project.properties["TMDB_API_KEY"]}\"")
        buildConfigField("String", "TMDB_BASE_URL", "\"${project.properties["TMDB_BASE_URL"]}\"")

        vectorDrawables { useSupportLibrary = true }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Default instrumentation runner for Android tests
    }

    buildTypes {
        debug {}
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        buildConfig = true // Enables access to BuildConfig constants
    }

    packaging {
        resources.excludes += "META-INF/**" // Exclude redundant META-INF files from final APK
    }
}

dependencies {

    // ------------------------------------------------------------------------
    // TESTING DEPENDENCIES
    // ------------------------------------------------------------------------
    implementation(libs.androidx.junit.ktx)
    testImplementation(kotlin("test"))                              // Unit testing
    testImplementation("junit:junit:4.13.2")                        // Unit testing
    testImplementation("app.cash.turbine:turbine:1.1.0")            // Asynchronous Flow testing (collect emissions easily)
    testImplementation("androidx.paging:paging-testing:3.3.6")      // Paging test utilities
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")   // Mocking library for Kotlin
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0") // Mock web server for network testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2") // Coroutine testing utilities

    // ------------------------------------------------------------------------
    // KAPT PROCESSORS
    // ------------------------------------------------------------------------

    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")


    // ------------------------------------------------------------------------
    // JETPACK COMPOSE (UI LAYER)
    // ------------------------------------------------------------------------

    val composeBom = platform("androidx.compose:compose-bom:2024.10.00")
    implementation(composeBom)

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.activity:activity-compose:1.9.2")  // Enables setContent{} in Compose
    implementation("androidx.compose.ui:ui")                    // Core Compose UI components
    implementation("androidx.compose.ui:ui-tooling-preview")    // Preview support in Android Studio
    debugImplementation("androidx.compose.ui:ui-tooling")       // Interactive previews
    implementation("androidx.compose.material3:material3")      // Material 3 design system
    implementation("androidx.compose.material:material")        // Needed for pullRefresh API


    // ------------------------------------------------------------------------
    // DAGGER HILT (Dependency Injection)
    // ------------------------------------------------------------------------

    implementation("com.google.dagger:hilt-android:2.57.2")          // Core DI
    kapt("com.google.dagger:hilt-android-compiler:2.57.2")           // Annotation processor
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")    // Compose Navigation integration


    // ------------------------------------------------------------------------
    // PAGING (Infinite Scroll)
    // ------------------------------------------------------------------------

    implementation("androidx.paging:paging-runtime:3.3.6")   // Core paging runtime
    implementation("androidx.paging:paging-compose:3.3.6")   // Paging integration with Compose


    // ------------------------------------------------------------------------
    // IMAGE LOADING (Coil)
    // ------------------------------------------------------------------------

    implementation("io.coil-kt:coil-compose:2.7.0") // Compose-compatible image loading with caching


    // ------------------------------------------------------------------------
    // JSON PARSING (Moshi + Retrofit)
    // ------------------------------------------------------------------------

    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")        // JSON → Kotlin data classes
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")  // Retrofit <-> Moshi converter


    // ------------------------------------------------------------------------
    // NETWORKING (Retrofit + OkHttp)
    // ------------------------------------------------------------------------

    implementation("com.squareup.retrofit2:retrofit:2.9.0")             // HTTP client
    implementation("com.squareup.okhttp3:okhttp:4.12.0")                // Core network layer
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")   // HTTP request logging


    // ------------------------------------------------------------------------
    // NAVIGATION (Compose Navigation)
    // ------------------------------------------------------------------------

    implementation("androidx.navigation:navigation-compose:2.8.3")


    // ------------------------------------------------------------------------
    // LIFECYCLE + COROUTINES
    // ------------------------------------------------------------------------

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")        // Lifecycle-aware coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")  // ViewModel support in Compose


    // ------------------------------------------------------------------------
    // MATERIAL DESIGN SUPPORT
    // ------------------------------------------------------------------------

    implementation("com.google.android.material:material:1.13.0") // Base material components
}