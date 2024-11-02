plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(projects.shared.resources)
            implementation(libs.mokoMvvmCore)
            implementation(libs.mokoMvvmFlow)
            implementation(libs.mokoMvvmLiveData)
            implementation(libs.ktorClient)
            implementation(libs.ktorClientJson)
            implementation(libs.ktorClientCio)
        }
    }
}

android {
    namespace = "com.chatkmm.features.base"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
