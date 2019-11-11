pluginManagement {
    val jibVersion = "1.7.0"
    val kotlinVersion = "1.3.50"
    val ktlintVersion = "9.1.0"
    val spotlessVersion = "3.25.0"
    val springBootVersion = "2.2.1.RELEASE"
    val versionsVersion = "0.27.0"

    plugins {
        kotlin("jvm") version kotlinVersion
        id("com.diffplug.gradle.spotless") version spotlessVersion
        id("com.github.ben-manes.versions") version versionsVersion
        id("com.google.cloud.tools.jib") version jibVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" ->
                    useModule("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        maven(url = "https://repo.spring.io/libs-milestone")
    }
}

rootProject.name = "traintracker"
