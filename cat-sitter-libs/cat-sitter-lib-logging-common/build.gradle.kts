plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.datetime)

    testImplementation(kotlin("test-junit"))
}
