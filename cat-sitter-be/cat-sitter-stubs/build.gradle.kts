plugins {
    kotlin("jvm")
}


dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":cat-sitter-common"))

    testImplementation(kotlin("test-junit"))
}