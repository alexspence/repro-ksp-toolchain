plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")

}

val kspVersion: String by project
dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":annotation"))
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")

    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")

    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")
}
