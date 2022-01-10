plugins {
    id("com.android.application")
    id("com.mikepenz.aboutlibraries.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("nl.bryanderidder.ornaguide")
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode(22)
        versionName("3.1")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }
    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    implementation("com.github.skydoves:transformationlayout:1.0.7")
    implementation("com.github.skydoves:bundler:1.0.3")
    implementation("nl.bryanderidder:themed-toggle-button-group:1.3.4")
    implementation("com.mikepenz:aboutlibraries-core:8.8.4")
    implementation("com.mikepenz:aboutlibraries:8.8.4")
    implementation("com.github.AppIntro:AppIntro:6.1.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:26.7.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx") {
        exclude(module = "play-services-ads-identifier")
    }

    // Room
    implementation("androidx.room:room-runtime:2.3.0")
    implementation("androidx.room:room-ktx:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    // Koin
    implementation("org.koin:koin-android:2.2.2")
    implementation("org.koin:koin-android-scope:2.2.2")
    implementation("org.koin:koin-android-viewmodel:2.2.2")

    // Network
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.github.skydoves:sandwich:1.0.9")

    // Databinding
    implementation("com.github.skydoves:bindables:1.0.5") {
        exclude(group = "com.google.android.material")
    }

    // Glide (caching images)
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.florent37:glidepalette:2.1.2")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    // Logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")

    // Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation("androidx.navigation:navigation-compose:1.0.0-alpha08")

    // Play store
    implementation("com.google.android.play:core:1.10.2")
    implementation("com.google.android.play:core-ktx:1.8.1")

    testImplementation("junit:junit:4.+")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}