plugins {
    alias(libs.plugins.kotlin)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.bundles.core)
}
