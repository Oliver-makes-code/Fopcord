plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.mod.dev.gradle)
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven("https://mvn.devos.one/releases/")
    maven("https://mvn.devos.one/snapshots/")
}

neoForge {
    neoFormVersion = "1.21.8-20250717.133445"

    parchment {
        minecraftVersion = "1.21.8"
        mappingsVersion = "2025.07.20"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.bundles.core)
}
