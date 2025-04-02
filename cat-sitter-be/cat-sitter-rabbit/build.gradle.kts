plugins {
    id("build-jvm")
    application
//    alias(libs.plugins.shadowJar)
    alias(libs.plugins.muschko.remote)
}

application {
    mainClass.set("ApplicationKt")
}

dependencies {
    implementation(kotlin("stdlib-common"))

    implementation(libs.rabbitmq.client)
    implementation(libs.jackson.databind)
    implementation(libs.logback)
    implementation(libs.coroutines.core)

    implementation(project(":cat-sitter-common"))
    implementation(project(":cat-sitter-app-common"))
    implementation("ru.catsitter.libs:cat-sitter-lib-logging-logback")
    implementation(project(":cat-sitter-api-jackson"))
    implementation(project(":cat-sitter-mappers"))
    implementation(project(":cat-sitter-biz"))
    implementation(project(":cat-sitter-stubs"))

    testImplementation(libs.testcontainers.rabbitmq)
    testImplementation(kotlin("test"))

}