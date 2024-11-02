plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "root"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(projects.shared.entity)
            implementation(projects.shared.resources)
            implementation(projects.shared.features.base)
            implementation(projects.shared.features.splash)
            implementation(projects.shared.features.authorization)
            implementation(projects.shared.features.chat)
            implementation(projects.shared.features.customer)
            implementation(projects.shared.features.menu)
            implementation(projects.shared.features.profile)
            implementation(projects.shared.features.registration)
            implementation(libs.mokoMvvmCore)
            implementation(libs.mokoMvvmFlow)
            implementation(libs.mokoMvvmLiveData)
            implementation(libs.ktorClient)
            implementation(libs.ktorClientJson)
            implementation(libs.ktorClientCio)
            implementation(libs.koinCore)
            implementation(libs.multiplatformSettings)
        }

        androidMain.dependencies {
            implementation(libs.koinCore)
            implementation(libs.koinAndroid)
            implementation(libs.multiplatformSettings)
            implementation(libs.gmsPlayServiceLocation)
        }

        iosMain.dependencies {
            implementation(libs.koinCore)
            implementation(libs.multiplatformSettings)
        }
    }
}

android {
    namespace = "com.chatkmm.features.root"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
