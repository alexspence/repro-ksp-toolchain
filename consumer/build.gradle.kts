plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    idea
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":annotation"))
    ksp(project(":annotation-processor"))

}

idea {
    module {
        // Not using += due to https://github.com/gradle/gradle/issues/8749
        sourceDirs = sourceDirs + file("build/generated/ksp/main/kotlin") // or tasks["kspKotlin"].destination
        testSourceDirs = testSourceDirs + file("build/generated/ksp/test/kotlin")
        generatedSourceDirs = generatedSourceDirs + file("build/generated/ksp/main/kotlin") + file("build/generated/ksp/test/kotlin")
    }
}