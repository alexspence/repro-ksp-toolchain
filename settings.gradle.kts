

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
    plugins {
        id("com.google.devtools.ksp") version "1.5.31-1.0.0"
        kotlin("jvm") version "1.5.31"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "repro-ksp-toolchain"
include("annotation")
include("annotation-processor")
include("consumer")
