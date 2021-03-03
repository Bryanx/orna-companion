plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("nl.bryanderidder.ornaguide")
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0")

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
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation("com.github.skydoves:transformationlayout:1.0.7")
    implementation("com.github.skydoves:bundler:1.0.3")

    // Room
    implementation("androidx.room:room-runtime:2.3.0-beta02")
    implementation("androidx.room:room-ktx:2.3.0-beta02")
    kapt("androidx.room:room-compiler:2.3.0-beta02")
    implementation("com.github.humazed:RoomAsset:1.0.3")

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
    implementation("com.github.skydoves:bindables:1.0.0") {
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

    testImplementation("junit:junit:4.+")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}