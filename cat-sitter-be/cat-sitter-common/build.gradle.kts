
plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.jackson.datatype)
    implementation(libs.jackson.kotlin)
    api(libs.kotlinx.datetime)
    implementation(kotlin("test"))
}