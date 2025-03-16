import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import io.ktor.plugin.features.*

plugins {
    alias(libs.plugins.muschko.remote)
    alias(libs.plugins.ktor)
    id("build-jvm")
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

ktor {
    configureNativeImage(project)
    docker {
        localImageName.set(project.name)
        imageTag.set(project.version.toString())
        jreVersion.set(JavaVersion.VERSION_21)
    }
}

jib {
    container.mainClass = application.mainClass.get()
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.server.headers.response)
    implementation(libs.ktor.server.headers.caching)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.server.headers.default)
    implementation(libs.ktor.server.test)
    implementation(libs.ktor.client.negotiation)

    implementation(libs.logback)

    implementation(kotlin("test-junit"))

    implementation(project(":cat-sitter-api-jackson"))
    implementation(project(":cat-sitter-app-common"))
    implementation(project(":cat-sitter-common"))
    implementation(project(":cat-sitter-mappers"))
    implementation(project(":cat-sitter-biz"))
    implementation("ru.catsitter.libs:cat-sitter-lib-logging-logback")
    implementation("ru.catsitter.libs:cat-sitter-lib-logging-common")

}

tasks {
    // ShadowJar — сборка fat-jar
    shadowJar {
        isZip64 = true
        archiveClassifier.set("all") // jar обычно будет называться что-то вроде myapp-all.jar
    }

    // Если возникает ошибка дублирования ресурсов (application.yaml),
    // пусть Gradle включает дубликаты:
    withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    // Создаём Dockerfile для JVM
    val dockerDockerfileJvm by creating(Dockerfile::class) {
        group = "docker"
        description = "Генерирует Dockerfile для запуска приложения под JVM"
        // Зависим от сборки shadowJar, чтобы jar был готов
        dependsOn(shadowJar)

        // Подставим название jar-файла.
        // Обычно shadowJar кладёт итог в build/libs/<project>-all.jar
        // или build/libs/<что-то>-all.jar
        // Можем узнать, как именно называется архив, так:
        val jarFileName = shadowJar.get().archiveFileName.get()

        // Указываем базовый образ с OpenJDK/JRE (amd64)
        from(Dockerfile.From("eclipse-temurin:17-jre").withPlatform("linux/amd64"))

        // Копируем jar в контейнер
        doFirst {
            // Скопируем готовый jar-файл во временную директорию, которую
            // использует сам плагин Dockerfile для дальнейшего копирования внутрь образа
            copy {
                from("$buildDir/libs/$jarFileName")
                into(destDir.get())
            }
            // Также скопируем application.yaml, если он лежит в корне проекта
            // (или, возможно, в другой папке - скорректируйте путь при необходимости)
            copy {
                from("src/main/resources/application.yaml")
                into(destDir.get())
            }
        }

        // А теперь - команды для Dockerfile:
        copyFile(jarFileName, "/app/$jarFileName")
        copyFile("application.yaml", "/app/application.yaml")
        exposePort(8080)
        workingDir("/app")
        entryPoint("java", "-jar", "/app/$jarFileName", "-config=./application.yaml")
    }

    // Определяем имя образа
    val registryUser: String? = System.getenv("CONTAINER_REGISTRY_USER")
    val registryPass: String? = System.getenv("CONTAINER_REGISTRY_PASS")
    val registryHost: String? = System.getenv("CONTAINER_REGISTRY_HOST")
    val registryPref: String? = System.getenv("CONTAINER_REGISTRY_PREF")

    // Если задан префикс для репозитория — используем его
    val imageName = registryPref?.let { "$it/${project.name}" } ?: project.name

    // Сборка Docker-образа
    val dockerBuildJvmImage by creating(DockerBuildImage::class) {
        group = "docker"
        description = "Собирает Docker-образ (JVM)"
        dependsOn(dockerDockerfileJvm)
        images.add("$imageName:${rootProject.version}")
        images.add("$imageName:latest")
        platform.set("linux/amd64")
    }

    // Публикация Docker-образа
    val dockerPushJvmImage by creating(DockerPushImage::class) {
        group = "docker"
        description = "Пушит Docker-образ (JVM)"
        dependsOn(dockerBuildJvmImage)
        images.set(dockerBuildJvmImage.images)

        registryCredentials {
            username.set(registryUser)
            password.set(registryPass)
            url.set("https://$registryHost/v1/")
        }
    }
}