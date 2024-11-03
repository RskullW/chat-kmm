plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("dev.icerock.mobile.multiplatform-network-generator")
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

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koinCore)
            implementation(libs.koinAndroid)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
            implementation(libs.material)
            implementation(libs.ktorClientOkHttp)
            implementation(libs.activityXml)
            implementation(libs.activityCompose)
        }
        commonMain.dependencies {
            implementation(projects.shared.entity)
            implementation(libs.ktorClient)
            implementation(libs.ktorClientJson)
            implementation(libs.ktorClientCio)
            implementation(libs.kotlinSerialization)
            implementation(libs.kotlinxDateTime)
            implementation(libs.mokoNetwork)
            implementation(libs.mokoNetworkEngine)
            implementation(libs.mokoNetworkErrors)
            implementation(libs.koinCore)
            implementation(libs.multiplatformSettings)
        }
        iosMain.dependencies {
            implementation(libs.ktorClientDarwin)
            implementation(libs.koinCore)
        }
    }
}

android {
    namespace = "com.chatkmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        buildConfigField("int", "VERSION_CODE", "1")
        buildConfigField("String", "VERSION_NAME", "\"1.0\"")
        externalNativeBuild {
            ndkBuild {
                cppFlags += ""
            }
        }
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/androidMain/kotlin/com/chatkmm/jni/Android.mk")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

mokoNetwork {
    spec("serverApi") {
        inputSpec = file("src/api/openapi.yaml")
    }
}


