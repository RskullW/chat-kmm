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
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "customer"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(projects.shared.entity)
            implementation(projects.shared.resources)
            implementation(projects.shared.features.base)
            implementation(libs.mokoMvvmCore)
            implementation(libs.mokoMvvmFlow)
            implementation(libs.mokoMvvmLiveData)
            implementation(libs.ktorClient)
            implementation(libs.ktorClientJson)
            implementation(libs.ktorClientCio)
            implementation(libs.koinCore)
            implementation(libs.mokoNetwork)
            implementation(libs.mokoNetworkErrors)
            implementation(libs.mokoNetworkEngine)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.chatkmm.features.customer"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
