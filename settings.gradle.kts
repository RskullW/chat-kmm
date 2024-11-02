enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Chat-KMM"
include(":androidApp")
include(":androidApp:features")
include(":androidApp:ui")
include(":androidApp:features:root")
include(":androidApp:features:screen:splash")
include(":shared")
include(":shared:entity")
include(":shared:resources")
include(":shared:features:root")
include(":shared:features:base")
include(":shared:features:splash")
include(":iosExport")
