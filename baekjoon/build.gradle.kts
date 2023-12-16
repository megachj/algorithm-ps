plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "sunset.algorithm"
version = "1.0.0"

repositories {
    mavenCentral()
}

apply {
    plugin("idea")
    plugin("kotlin")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
