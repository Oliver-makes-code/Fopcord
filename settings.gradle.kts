dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/" )
        maven("https://mvn.devos.one/releases/")
        maven("https://mvn.devos.one/snapshots/")
        maven("https://maven.neoforged.net/releases")
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.fabricmc.net/" )
        maven("https://mvn.devos.one/releases/")
        maven("https://mvn.devos.one/snapshots/")
        maven("https://maven.neoforged.net/releases")
    }
}

include(":core")
include(":1.21.8:common")
include(":1.21.8:fabric")

rootProject.name = "Fopcord"
