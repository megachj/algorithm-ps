plugins {
    // kotlin("jvm")
    id("java")
}

group = "sunset.algorithm"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
//kotlin {
//    jvmToolchain(11)
//}