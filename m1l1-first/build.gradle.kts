plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}