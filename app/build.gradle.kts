plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("androidx.navigation.safeargs")
    id("kotlin-kapt")

}

android {
    namespace = "com.tayyipgunay.navigationart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tayyipgunay.navigationart"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}





        dependencies {
            implementation(libs.androidx.core.ktx.v1120)
            implementation(libs.androidx.appcompat.v161)
            implementation(libs.material.v1100)
            implementation("androidx.constraintlayout:constraintlayout:2.1.4")
            testImplementation("junit:junit:4.13.2")
            androidTestImplementation("androidx.test.ext:junit:1.1.5")
            androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

            val lifecycle_version = "2.6.1"
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
            implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

           /* val room_version = "2.5.2"
            implementation("androidx.room:room-runtime:$room_version")
            implementation("androidx.room:room-ktx:$room_version")
            kapt("androidx.room:room-compiler:$room_version")
            annotationProcessor("androidx.room:room-compiler:$room_version")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")*/
            val room_version = "2.6.1"
            val coroutinesVersion = "1.5.2"
            implementation("androidx.room:room-runtime:$room_version")
            implementation("androidx.room:room-ktx:$room_version")
            kapt("androidx.room:room-compiler:$room_version")
            annotationProcessor("androidx.room:room-compiler:$room_version")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")





            val navVersion = "2.7.0"
            implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
            implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

            val retrofitVersion = "2.9.0"
            implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
            implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
            implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

            // RxJava 3
            implementation("io.reactivex.rxjava3:rxjava:3.1.5")
            implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
            implementation("androidx.room:room-rxjava3:$room_version")

            val glideVersion = "4.15.1"
            implementation("com.github.bumptech.glide:glide:$glideVersion")

            // AndroidX'in destek kütüphanelerine geçiş
            implementation("androidx.palette:palette:1.0.0")
            implementation("com.google.android.material:material:1.10.0")

            val preferencesVersion = "1.2.1"
            implementation("androidx.preference:preference-ktx:$preferencesVersion")
        }

    



