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
include(":androidApp:features:screen:profile")
include(":androidApp:features:screen:authorization")
include(":androidApp:features:screen:registration")
include(":androidApp:features:screen:menu")
include(":androidApp:features:screen:chat")
include(":shared")
include(":shared:entity")
include(":shared:resources")
include(":shared:features:root")
include(":shared:features:base")
include(":shared:features:splash")
include(":shared:features:profile")
include(":shared:features:authorization")
include(":shared:features:registration")
include(":shared:features:menu")
include(":shared:features:chat")
include(":shared:features:customer")
include(":iosExport")
