rootProject.name = "cat-sitter"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

includeBuild("cat-sitter-be")
includeBuild("cat-sitter-libs")