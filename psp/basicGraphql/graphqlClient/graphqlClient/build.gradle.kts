import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("com.apollographql.apollo3").version("3.7.3")
    id("org.openjfx.javafxplugin").version("0.0.9")
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainModule.set("graphqlClient")
    mainClass.set("org.example.ui.MainFx")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.apollographql.apollo3:apollo-runtime:3.7.3")
    implementation("com.apollographql.apollo3:apollo-normalized-cache-sqlite:3.7.3")
    implementation("com.apollographql.apollo3:apollo-api:3.7.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")

    implementation("org.openjfx:javafx-base:17")
    implementation("org.openjfx:javafx-fxml:17")


    implementation("org.projectlombok:lombok:1.18.22")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {
    jvmToolchain(11)
}

javafx {
    version = "17.0.1"
    modules = listOf("javafx.controls","javafx.fxml")
}

apollo {
    service("warehouse") {
        sourceFolder.set("org/example/warehouse")
        packageName.set("org.example.warehouse")
    }
    // instruct the compiler to generate Kotlin models
    generateKotlinModels.set(true)
}

application {
    mainClass.set("MainFx")
}