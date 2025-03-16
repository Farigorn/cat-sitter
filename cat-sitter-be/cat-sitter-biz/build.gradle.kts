plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":cat-sitter-common"))
    implementation(project(":cat-sitter-mappers"))
    implementation(project(":cat-sitter-stubs"))
    implementation(kotlin("test-junit"))

}