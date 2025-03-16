plugins {
    id("build-jvm")
}


group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":cat-sitter-api-jackson"))
    implementation(project(":cat-sitter-common"))

    testImplementation(kotlin("test-junit"))
}