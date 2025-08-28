import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.loom)
}

base {
    archivesName.set(project.property("archives_base_name").toString())
}

version = project.property("version").toString() + "+mc.1.21.8-fabric"

val includeInJar: Configuration by configurations.creating

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/" )
    maven("https://mvn.devos.one/releases/")
    maven("https://mvn.devos.one/snapshots/")
}

loom {
    serverOnlyMinecraftJar()

    mods {
        register("fopbot") {
            sourceSet("main")
        }
    }

    runs {
        named("server") {
            configName = "Fabric 1.21.8 Server"
        }

        configureEach {
            isIdeConfigGenerated = true
            appendProjectPathToConfigName = false

            property("mixin.debug.export", "true")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.8")
    mappings(loom.officialMojangMappings())
    modImplementation(libs.bundles.fabric.mc1218)
    implementation(libs.bundles.core)
    compileOnly(project(":1.21.8:common"))
    implementation(project(":core"))
    includeInJar(libs.kord.core)
    includeInJar(libs.tiny.codecs)
    includeInJar(libs.tiny.json)
}

tasks.named<ProcessResources>("processResources") {
    val properties: Map<String, Any> = mapOf(
        "version" to project.property("version").toString(),
        "loader_version" to libs.versions.fabric.loader.get(),
        "fapi_version" to libs.versions.fabric.api.mc1218.get(),
        "minecraft_version" to "1.21.8"
    )

    inputs.properties(properties)

    filesMatching(listOf("fabric.mod.json")) {
        expand(properties)
    }
}

tasks.withType(KotlinCompile::class.java).configureEach {
    source(
        project(":1.21.8:common").sourceSets.main.get().allSource,
        project(":core").sourceSets.main.get().allSource
    )
}
tasks.withType(JavaCompile::class.java).configureEach {
    source(
        project(":1.21.8:common").sourceSets.main.get().allSource
    )
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from({
        includeInJar.map {
            if (it.isDirectory) it else zipTree(it)
        }
    })
}
