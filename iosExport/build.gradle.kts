plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("dev.icerock.mobile.multiplatform-network-generator")
}

val features = listOf(
    projects.shared,
    projects.shared.entity,
    projects.shared.resources,
    projects.shared.features.splash,
    projects.shared.features.root,
    projects.shared.features.base,
)

val apiLibs = listOf(
    libs.kotlinxDateTime,
    libs.kotlinSerialization,
    libs.mokoMvvmCore,
    libs.mokoMvvmFlow,
    libs.mokoMvvmLiveData,
    libs.mokoGraphics,
    libs.mokoNetwork,
    libs.mokoNetworkEngine,
    libs.mokoNetworkErrors,
    libs.ktorClient,
    libs.ktorClientJson,
    libs.ktorClientCio,
    libs.threetenabp,
    libs.koinCore,
    libs.multiplatformSettings,
)

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

    cocoapods {
        summary = "Multiplatform Library for iOS App"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "iosExport"

            features.forEach(::export)
            apiLibs.forEach(::export)
        }
    }

    sourceSets {
        commonMain.dependencies {
            features.forEach { api(it) }
            apiLibs.forEach { api(it) }
        }

        iosMain.dependencies {
            apiLibs.forEach(::implementation)
        }
    }
}

android {
    namespace = "com.chatkmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

