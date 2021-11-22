plugins {
    kotlin("jvm") version "1.6.0"
    id("com.google.devtools.ksp") version "1.6.0-1.0.1"
    idea
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects {
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension> {
            jvmToolchain {
                (this as JavaToolchainSpec).apply {
                    languageVersion.set(JavaLanguageVersion.of(17))
                }
            }
        }
    }
}

subprojects {



    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
        }
    }
}
