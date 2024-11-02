plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.chatkmm.features.root"
    compileSdk = 34

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val featureModules = listOf(
    projects.androidApp.features.screen.splash,
    projects.androidApp.features.screen.authorization,
    projects.androidApp.features.screen.chat,
    projects.androidApp.features.screen.menu,
    projects.androidApp.features.screen.profile,
    projects.androidApp.features.screen.registration,
)

val sharedModules = listOf(
    projects.shared.features.root,
    projects.shared.features.base,
    projects.shared.entity,
    projects.shared,
)

dependencies {
    featureModules.forEach { module ->
        implementation(module)
    }

    sharedModules.forEach { module ->
        implementation(module)
    }

    implementation(libs.mokoMvvmCore)
    implementation(libs.mokoMvvmFlow)
    implementation(libs.mokoMvvmLiveData)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.navigation)
    implementation(libs.material)
    implementation(libs.koinCore)
    implementation(libs.koinAndroid)
}