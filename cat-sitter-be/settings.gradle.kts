rootProject.name ="cat-sitter-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm")
        id("build-multiplatform")
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":cat-sitter-api-jackson")
include(":cat-sitter-mappers")
include(":cat-sitter-common")
include(":cat-sitter-app-ktor")
include(":cat-sitter-app-common")
include(":cat-sitter-stubs")
include(":cat-sitter-biz")
include(":cat-sitter-api-log")
include(":cat-sitter-rabbit")