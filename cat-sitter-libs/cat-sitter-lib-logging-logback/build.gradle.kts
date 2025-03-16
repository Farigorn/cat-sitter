plugins {
    id("build-jvm")
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback)

    implementation(libs.logback.logstash)
    api(libs.logback.appenders)
    api(libs.logger.fluentd)

    implementation(project(":cat-sitter-lib-logging-common"))
    testImplementation(kotlin("test-junit"))
}