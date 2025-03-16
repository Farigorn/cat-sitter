plugins {
    kotlin("jvm")
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":cat-sitter-common"))
    implementation(project(":cat-sitter-api-log"))
    implementation(project(":cat-sitter-biz"))
    testImplementation(kotlin("test-junit"))
}
